package Client;

import Client.DrawActions.IDrawAction;

import java.util.ArrayList;

public class ActionQueue extends ArrayList<IDrawAction> {
    @Override
    public boolean add(IDrawAction drawAction){
        System.out.println(drawAction);
        return super.add(drawAction);
    }
}
