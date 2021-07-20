package springboot.Model.MetaModel;

import springboot.Entity.TeacherEntity_;

public class TeacherMeta extends TeacherEntity_ {
    public static boolean hasAttribute(String attr){
        switch (attr){
            case TeacherMeta.ADDRESS:
            case TeacherMeta.C:
            case TeacherMeta.EMAIL:
            case TeacherMeta.ID:
            case TeacherMeta.FULLNAME:
            case TeacherMeta.GRADE:
            case TeacherMeta.PHONE:
                return true;
            default:
                return false;
        }
    }
}
