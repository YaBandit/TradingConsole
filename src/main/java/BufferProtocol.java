import java.util.HashMap;
import java.util.Map;

/**
 * Created by dylan on 7/4/2015.
 */
public enum BufferProtocol {

    REGISTER_REQUST(1),
    REGISTER_ACCEPTED(2),
    REGISTER_REJECTED(3),
    ORDER_MARKET(4),
    ORDER_LIMIT(5),
    ORDER_ACK(6),
    ORDER_REJECT(7);

    private final int value;

    private static final Map<Integer, BufferProtocol> typesByValue = new HashMap<Integer, BufferProtocol>();

    static {
        for (BufferProtocol type : BufferProtocol.values()) {
            typesByValue.put(type.value, type);
        }
    }

    BufferProtocol(final int value) {
        this.value = value;
    }

    public static BufferProtocol forValue(int value) {
        return typesByValue.get(value);
    }

    public int getValue() { return value; }

}
