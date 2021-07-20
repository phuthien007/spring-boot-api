package springboot.Model.MetaModel;

import springboot.Entity.CourseEntity_;

public class CourseMeta extends CourseEntity_ {
    public static boolean hasAttribute(String attr){
        switch (attr){
            case CourseMeta.CREATE_DATE:
            case CourseMeta.ID:
            case CourseMeta.EXAM:
            case CourseMeta.NAME:
            case CourseMeta.TYPE:
                return true;
            default:
                return false;
        }
    }
}
