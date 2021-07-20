package springboot.Model.MetaModel;

import springboot.Entity.ExamEntity_;

public class ExamMeta extends ExamEntity_ {
    public static boolean hasAttribute(String attr){
        switch (attr){
            case ExamMeta.EXAM_RESULT:
            case ExamMeta.COURSE:
            case ExamMeta.ID:
            case ExamMeta.NAME:
                return true;
            default:
                return false;
        }
    }
}
