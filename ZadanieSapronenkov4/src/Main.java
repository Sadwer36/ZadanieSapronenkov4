//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        RoomService<Room> service = new RoomServiceImpl<>();

        Room room1 = new StandardRoom(101, 2, 100, false);
        Room room2 = new LuxRoom(202, 3, 250, false);
        Room room3 = new UltraLuxRoom(303, 4, 500, true);

        System.out.println(room1);
        System.out.println(room2);
        System.out.println(room3);

        service.clean(room1);
        service.clean(room2);

        service.reserve(room1);

        service.free(room3);
        service.reserve(room3);
    }
}

abstract class Room {
    protected int number;
    protected int maxPeople;
    protected int pricePerNight;
    protected boolean isReserved;

    public Room(int number, int maxPeople, int pricePerNight, boolean isReserved) {
        this.number = number;
        this.maxPeople = maxPeople;
        this.pricePerNight = pricePerNight;
        this.isReserved = isReserved;
    }

    public int getNumber() {
        return number;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [№" + number +
                ", maxPeople=" + maxPeople +
                ", price=" + pricePerNight +
                ", reserved=" + isReserved +
                "]";
    }
}

class EconomyRoom extends Room {
    public EconomyRoom(int number, int maxPeople, int price, boolean isReserved) {
        super(number, maxPeople, price, isReserved);
    }
}

class StandardRoom extends EconomyRoom {
    public StandardRoom(int number, int maxPeople, int price, boolean isReserved) {
        super(number, maxPeople, price, isReserved);
    }
}

abstract class ProRoom extends Room {
    public ProRoom(int number, int maxPeople, int price, boolean isReserved) {
        super(number, maxPeople, price, isReserved);
    }
}

class LuxRoom extends ProRoom {
    public LuxRoom(int number, int maxPeople, int price, boolean isReserved) {
        super(number, maxPeople, price, isReserved);
    }
}

class UltraLuxRoom extends LuxRoom {
    public UltraLuxRoom(int number, int maxPeople, int price, boolean isReserved) {
        super(number, maxPeople, price, isReserved);
    }
}

interface RoomService<T extends Room> {
    void clean(T room);
    void reserve(T room);
    void free(T room);
}

class RoomServiceImpl<T extends Room> implements RoomService<T> {

    @Override
    public void clean(T room) {
        System.out.println("Комната " + room.getNumber() + " очищена.");
    }

    @Override
    public void reserve(T room) {
        if (room.isReserved()) {
            throw new RoomAlreadyReservedException(
                    "Комната №" + room.getNumber() + " уже забронирована!"
            );
        }
        room.setReserved(true);
        System.out.println("Комната " + room.getNumber() + " успешно забронирована.");
    }

    @Override
    public void free(T room) {
        room.setReserved(false);
        System.out.println("Комната " + room.getNumber() + " освобождена.");
    }
}

class RoomAlreadyReservedException extends RuntimeException {
    public RoomAlreadyReservedException(String message) {
        super(message);
    }
}
