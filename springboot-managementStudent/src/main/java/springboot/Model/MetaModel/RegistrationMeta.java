package springboot.Model.MetaModel;

import springboot.Entity.RegistrationEntity_;

public class RegistrationMeta extends RegistrationEntity_ {
    public static boolean hasAttribute(String attr){
        switch (attr){
            case RegistrationMeta.C:
            case RegistrationMeta.REGISTER_DAY:
            case RegistrationMeta.ID:
            case RegistrationMeta.CREATE_DATE:
            case RegistrationMeta.S:
            case RegistrationMeta.STATUS:
                return true;
            default:
                return false;
        }
    }
}
