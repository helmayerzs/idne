package hu.idne.backend.utils.business;

public class OrderNoUtils {

    private OrderNoUtils(){}

    public static String normalize(String orderNo){

        return orderNo.length() > 8
                ? new StringBuilder(new StringBuilder(orderNo)
                .reverse().toString()
                .substring(0,8)).reverse().toString()
                : orderNo;
    }
}
