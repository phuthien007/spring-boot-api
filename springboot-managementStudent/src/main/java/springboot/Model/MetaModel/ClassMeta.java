package springboot.Model.MetaModel;

import springboot.Entity.ClassEntity_;

public class ClassMeta extends ClassEntity_ {
    public static boolean hasAttribute(String attr){
        switch (attr){
            case ClassMeta.NAME:
            case ClassMeta.COURSE:
            case ClassMeta.END_DATE:
            case ClassMeta.EVENT:
            case ClassMeta.TEACHER:
            case ClassMeta.EXAM_RESULT:
            case ClassMeta.START_DATE:
            case ClassMeta.STATUS:
            case ClassMeta.STUDENT:
                return true;
            default:
                return false;
        }
    }
}
