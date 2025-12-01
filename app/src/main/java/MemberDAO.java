import java.sql.*;
import java.time.LocalDate;

public class MemberDAO extends Account {

    public MemberDAO() {}

    public int createGoal(String goal_type, double target_value, LocalDate end) {

        String query =
                "INSERT INTO goal (goal_type, account_id, target_value, start_date, target_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = prepare(query, true)) {
            ps.setString(1, goal_type);
            ps.setInt(2, accountId);
            ps.setDouble(3, target_value);
            ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            ps.setDate(5, java.sql.Date.valueOf(end));

            if (ps.executeUpdate() == 0) return 0;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }

            return 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public void getMemberGoals(int account_id) {
        String query = "SELECT * FROM goal WHERE account_id = ?";

        try (PreparedStatement ps = prepare(query, false)) {
            ps.setInt(1, account_id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("- Goal: " + rs.getString("goal_type") + " | Value: " + rs.getString("target_value") + " | By: " + rs.getDate("target_date"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public int logHealthMetric(String metric_name, double metric_value) {
        String query =
                "INSERT INTO healthmetric (account_id, metric_name, metric_value) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement ps = prepare(query, true)) {
            ps.setInt(1, accountId);
            ps.setString(2, metric_name);
            ps.setDouble(3, metric_value);

            if (ps.executeUpdate() == 0) return 0;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }

            return 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public void getRecentHealthMetrics() {
        String query  =
                "SELECT DISTINCT ON (metric_name) * " +
                "FROM healthmetric " +
                "WHERE account_id = ? " +
                "ORDER BY metric_name, recorded_at DESC";

        try (PreparedStatement ps = prepare(query, false)) {

            ps.setInt(1, this.accountId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Metric: " + rs.getString("metric_name") + " | Value: " + rs.getDouble("metric_value") + " | Date: " + rs.getTimestamp("recorded_at"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public int countCompletedBookings () {
        String query =
                "SELECT COUNT(*) " +
                "FROM registration r " +
                "LEFT JOIN booking b " +
                "ON r.booking_id = b.booking_id " +
                "WHERE r.account_id = ? AND b.end_time < NOW()";

        try (PreparedStatement ps = prepare(query, false)) {

            ps.setInt(1, this.accountId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }
    public void getUpcomingClasses() {
        String query  =
                "SELECT booking_type, booking_title, start_time, end_time, room.title FROM booking " +
                "INNER JOIN registration ON registration.booking_id = booking.booking_id " +
                "LEFT JOIN room ON booking.room_id = room.room_id " +
                "WHERE registration.account_id = ? AND booking.start_time > NOW()";

        try (PreparedStatement ps = prepare(query, false)) {

            ps.setInt(1, this.accountId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Class: " + rs.getString("booking_title") +
                            " | Type: " + rs.getDouble("booking_type") +
                            " | Time: " + rs.getDate("start_time") +
                            " to " + rs.getTimestamp("end_time") +
                            " | Room: " + rs.getString("room.room_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void getDashBoard() {
        System.out.println("Dash Board:");
        getRecentHealthMetrics();
        getMemberGoals(this.accountId);
        System.out.println("Completed Classes: " + countCompletedBookings());
        getUpcomingClasses();
    }
}
