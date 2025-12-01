import java.sql.*;

public class TrainerDAO extends Account {

    public TrainerDAO() {}
    public void viewAssignments() {
        String query = "SELECT * FROM booking b " +
                "INNER JOIN account a ON b.account_id = a.account_id " +
                "WHERE a.account_id = ?";

        try (PreparedStatement ps = prepare(query, false)) {
            ps.setInt(1, this.accountId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Booking: " + rs.getString("booking_title") + " | Class Type: " + rs.getString("booking_type") +
                            " | " + rs.getDate("start_time") + " to " + rs.getDate("end_time"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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
    public void lookupMember(String first_name, String last_name) {
        String query = "SELECT DISTINCT ON (a.account_id) *" +
                "FROM account a LEFT JOIN healthmetric h ON a.account_id = h.account_id " +
                "WHERE a.first_name ILIKE ? AND a.last_name ILIKE ? " +
                "ORDER BY a.account_id, h.recorded_at DESC";

        try (PreparedStatement ps = prepare(query, false)) {
            ps.setString(1, first_name);
            ps.setString(2, last_name);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Member: " + rs.getString("first_name") + " " + rs.getString("last_name") +
                            " | Latest Metric: " + rs.getString("metric_name") + " " + rs.getDouble("metric_value") + " | Current Goals: ");
                    getMemberGoals(rs.getInt("account_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
