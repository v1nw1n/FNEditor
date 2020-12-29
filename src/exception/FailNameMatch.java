package exception;

/**
 * @author vincent
 */
public class FailNameMatch extends Exception {
    @Override
    public String toString() {
        return "无效的文件名匹配";
    }
}
