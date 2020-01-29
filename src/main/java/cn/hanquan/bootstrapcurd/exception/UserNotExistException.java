package cn.hanquan.bootstrapcurd.exception;

public class UserNotExistException extends RuntimeException {

    public UserNotExistException() {
        super("aaa用户，你用户不存在，哈哈");
    }
}
