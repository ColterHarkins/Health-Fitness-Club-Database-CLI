import java.sql.*;

public class Account {
    protected int accountId;
    protected String role;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;

    protected PreparedStatement prepare(String sql, boolean returnKeys) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        if (returnKeys) {
            return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }
        return connection.prepareStatement(sql);
    }

    protected Account signIn(int accountId) {
        this.accountId = accountId;

        String query = "SELECT * FROM account WHERE account_id = ?";
        try (PreparedStatement ps = prepare(query, false)) {
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.role = rs.getString("role");
                this.firstName = rs.getString("first_name");
                this.lastName = rs.getString("last_name");
                this.email = rs.getString("email");
                this.phoneNumber = rs.getString("phone_number");
                return this;
            }
            return null;

        } catch (SQLException e) {
            System.err.println("Failed to connect to the database");
            return null;
        }
    }

    protected Account signUp(String role, String firstName, String lastName, String email, String phoneNumber) {

        // Check if this account is already logged in
        if (this.accountId > 0) {
            System.out.println("Already logged in as " + this.role + " account for " + this.firstName + " " + this.lastName);
            return this;
        }

        // Check if the role is a real account type
        if (!(role.equals("member") || role.equals("trainer") || role.equals("admin"))) {
            System.out.println(role + " is an invalid role");
            return null;
        }

        String query =
                "INSERT INTO account (first_name, last_name, email, phone_number, role) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = prepare(query, true)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, phoneNumber);
            ps.setString(5, role);
            if (ps.executeUpdate() == 0) {
                return null;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    this.accountId = keys.getInt(1);
                    this.role = role;
                    this.firstName = firstName;
                    this.lastName = lastName;
                    this.email = email;
                    this.phoneNumber = phoneNumber;
                    System.out.println("Successfully created " + this.role + " account for " + this.firstName + " " + this.lastName + " with ID " + this.accountId);
                    return this;
                }
            }
            return null;

        } catch (SQLException e) {
            System.out.println("Error registering account: " + e.getMessage());
            return null;
        }
    }

    public boolean changeName(String first_name, String last_name) {
        String query =
                "UPDATE account " +
                "SET first_name = ?, last_name = ? " +
                "WHERE account_id = ?";

        try (PreparedStatement ps = prepare(query, false)) {
            ps.setString(1, first_name);
            ps.setString(2, last_name);
            ps.setInt(3, accountId);
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public boolean changePhone(String phone_number) {
        String query =
                "UPDATE account " +
                "SET phone_number = ? " +
                "WHERE account_id = ?";

        try (PreparedStatement ps = prepare(query, false)) {
            ps.setString(1, phone_number);
            ps.setInt(2, accountId);
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public boolean changeEmail(String email) {
        String query =
                "UPDATE account " +
                "SET email = ? " +
                "WHERE account_id = ?";

        try (PreparedStatement ps = prepare(query, false)) {
            ps.setString(1, email);
            ps.setInt(2, accountId);
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public boolean emailExists(String email) {
        String query =
                "SELECT * FROM account WHERE email = ?";

        try (PreparedStatement ps = prepare(query, false)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public int getAccountId() { return accountId; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getRole() { return role; }
}
