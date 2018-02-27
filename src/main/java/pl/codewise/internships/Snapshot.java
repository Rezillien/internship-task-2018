package pl.codewise.internships;

import java.util.List;

public class Snapshot {
    private List<Message> list;

    public Snapshot(List<Message> list){
        this.list=list;
    }

    public List<Message> getMessages(){
        return list;
    }

}
