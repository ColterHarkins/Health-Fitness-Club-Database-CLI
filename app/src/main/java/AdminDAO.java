import java.sql.*;

public class AdminDAO extends Account {

    public AdminDAO() {}

    public int addTrainer(String first_name, String last_name, String email, String phone) {
        TrainerDAO trainer = new TrainerDAO();
        return trainer.signUp("trainer",  first_name, last_name, email, phone).getAccountId();
    }
    public int addRoom(String title, int capacity) {
        String query = "INSERT INTO room (title, capacity) VALUES (?, ?)";

        try (PreparedStatement ps = prepare(query, true)) {
            ps.setString(1, title);
            ps.setInt(2, capacity);

            if (ps.executeUpdate() == 0) {
                return 0;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            return 0;

        } catch(SQLException e) {
            System.out.println("Error registering room: " + e.getMessage());
            return 0;
        }
    }
    public boolean isRoomBooked(int roomId, Timestamp start, Timestamp end) {
        String query =
                "SELECT COUNT(*) " +
                "FROM booking " +
                "WHERE room_id = ? AND (start_time < ? AND end_time > ?)";

        try (PreparedStatement ps = prepare(query, false)) {
            ps.setInt(1, roomId);
            ps.setTimestamp(2, end);
            ps.setTimestamp(3, start);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking room booking: " + e.getMessage());
        }
        return false;
    }
    public int scheduleClass(String booking_type, String booking_title, int room_id, int trainer_id, Timestamp start_time, Timestamp end_time) {
        if (isRoomBooked(room_id, start_time, end_time)) return 0;

        String query =
                "INSERT INTO booking (booking_type, booking_title, room_id, account_id, start_time, end_time) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = prepare(query, true)) {
            ps.setString(1, booking_type);
            ps.setString(2, booking_title);
            ps.setInt(3, room_id);
            ps.setInt(4, trainer_id);
            ps.setTimestamp(5, start_time);
            ps.setTimestamp(6, end_time);
            if (ps.executeUpdate() == 0) {
                return 0;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    System.out.println("Created booking with ID: " + keys.getInt(1) + " successfully!");
                    return keys.getInt(1);
                }
            }
            return 0;


        } catch(SQLException e) {
            System.out.println("Error registering booking: " + e.getMessage());
            return 0;
        }
    }
    public void viewRooms() {
        String query = "SELECT * FROM room";

        try (PreparedStatement ps = prepare(query, false)) {
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Rooms:");
                while (rs.next()) System.out.println(" -" + rs.getString("title") + " | Capacity:  " + rs.getInt("capacity"));
            }

        } catch(SQLException e) {
            System.out.println("Error registering room: " + e.getMessage());
        }
    }
}