package springboot.Model.MetaModel;

import springboot.Entity.ExamResultEntity_;

public class ExamResultMeta extends ExamResultEntity_ {
    public static boolean hasAttribute(String attr){
        switch (attr){
            case ExamResultMeta.EXAM:
            case ExamResultMeta.C:
            case ExamResultMeta.RESULT_DATE:
            case ExamResultMeta.ID:
            case ExamResultMeta.NOTE:
            case ExamResultMeta.SCORE:
            case ExamResultMeta.STUDENT:
                return true;
            default:
                return false;
        }
    }
}
