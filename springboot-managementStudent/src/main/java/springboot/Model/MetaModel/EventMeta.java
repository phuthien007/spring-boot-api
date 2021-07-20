package springboot.Model.MetaModel;

import springboot.Entity.EventEntity_;

public class EventMeta extends EventEntity_ {
    public static boolean hasAttribute(String attr){
        switch (attr){
            case EventMeta.C:
            case EventMeta.CREATE_DATE:
            case EventMeta.ID:
            case EventMeta.HAPPEN_DATE:
            case EventMeta.NAME:
            case EventMeta.STATUS:
                return true;
            default:
                return false;
        }
    }
}
