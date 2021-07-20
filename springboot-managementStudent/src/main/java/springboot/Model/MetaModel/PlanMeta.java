package springboot.Model.MetaModel;

import springboot.Entity.PlanEntity_;

public class PlanMeta extends PlanEntity_ {
    public static boolean hasAttribute(String attr){
        switch (attr){
            case PlanMeta.COURSE:
            case PlanMeta.ID:
            case PlanMeta.NAME:
                return true;
            default:
                return false;
        }
    }
}
