package springboot.Model.MetaModel;

import springboot.Entity.StudentEntity_;

public class StudentMeta extends StudentEntity_ {
    public static boolean hasAttribute(String attr){
        switch (attr){
            case StudentMeta.ADDRESS:
            case StudentMeta.C:
            case StudentMeta.BIRTHDAY:
            case StudentMeta.CREATE_DATE:
            case StudentMeta.EMAIL:
            case StudentMeta.EXAM_RESULT:
            case StudentMeta.FACEBOOK:
            case StudentMeta.FULLNAME:
            case StudentMeta.ID:
            case StudentMeta.NOTE:
            case StudentMeta.PHONE:
                return true;
            default:
                return false;
        }
    }
}
