package pl.codewise.internships;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingConcurrentMessageQueue implements MessageQueue {
    private final static int minutesToTimeout = 5;

    //non blocking concurrent queue
    private Queue<MessageWithTimestamp> queue = new LinkedBlockingQueue<MessageWithTimestamp>();

    private long errorMessagesCount=0;

    private Lock lock = new ReentrantLock();



    public void add(Message message) {
        lock.lock();
        queue.add(new MessageWithTimestamp(message));
        if(isErrorMessage(message.getErrorCode()))
            errorMessagesCount++;
        lock.unlock();
    }

    public Snapshot snapshot() {
        lock.lock();
        cleanTimeoutMessages();
        Iterator<MessageWithTimestamp> iterator = queue.iterator();
        List<Message> list = new ArrayList<>();
        for(int i=0;i<100 && iterator.hasNext();i++){
            list.add(iterator.next());
        }
        lock.unlock();
        return new Snapshot(list);

    }

    public long numberOfErrorMessages() {
        lock.lock();
        cleanTimeoutMessages();
        long errorMessagesCount = this.errorMessagesCount;
        lock.unlock();
        return errorMessagesCount;
    }

    private void cleanTimeoutMessages(){
        if(queue.isEmpty())
            return;
        while(queue.peek().isOlderThan(minutesToTimeout)){
            if(isErrorMessage(queue.peek().getErrorCode())){
                errorMessagesCount--;
            }
            queue.remove();
        }
    }

    private boolean isErrorMessage(int code){
        return code >= 400 && code < 600;
    }
}
