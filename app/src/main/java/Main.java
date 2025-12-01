    import java.sql.Timestamp;
    import java.time.LocalDate;
    import java.util.Scanner;

    public class Main {

        private static final Scanner sc = new Scanner(System.in);

        public static void main(String[] args) {
            System.out.println("=== Health & Fitness Club ===");

            while (true) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Member Login");
                System.out.println("2. Member Sign Up");
                System.out.println("3. Trainer Login");
                System.out.println("4. Admin Login");
                System.out.println("5. Exit");
                System.out.print("Choose: ");

                int choice = readInt();

                switch (choice) {
                    case 1 -> memberLogin();
                    case 2 -> memberSignup();
                    case 3 -> trainerLogin();
                    case 4 -> adminLogin();
                    case 5 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        }

    //+=============================+MEMBER OPERATIONS+=======================================||
        private static void memberSignup() {
            System.out.println("\n=== Member Sign Up ===");

            System.out.print("First name: ");
            String fn = sc.nextLine();
            System.out.print("Last name: ");
            String ln = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Phone: ");
            String phone = sc.nextLine();

            MemberDAO member = new MemberDAO();
            Account acc = member.signUp("member", fn, ln, email, phone);

            if (acc != null) {
                System.out.println("Signup successful.");
                memberMenu(member);
            }
        }

        private static void memberLogin() {
            System.out.print("\nEnter your account ID: ");
            int id = readInt();

            MemberDAO member = new MemberDAO();

            if (member.signIn(id) == null || !"member".equals(member.getRole())) {
                System.out.println("Invalid member ID.");
                return;
            }

            memberMenu(member);
        }

        private static void memberMenu(MemberDAO m) {
            System.out.println("\nWelcome, Member " + m.getFullName());

            while (true) {
                System.out.println("\n--- Member Menu ---");
                System.out.println("1. Log Health Metric");
                System.out.println("2. Create Fitness Goal");
                System.out.println("3. View Dashboard");
                System.out.println("4. Settings");
                System.out.println("5. Logout");
                System.out.print("Choose: ");

                int choice = readInt();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Metric name: ");
                        String metric = sc.nextLine();
                        System.out.print("Value: ");
                        double val = readDouble();
                        m.logHealthMetric(metric, val);
                    }
                    case 2 -> {
                        System.out.print("Goal name: ");
                        String name = sc.nextLine();
                        System.out.print("Target value: ");
                        double target = readDouble();
                        System.out.print("End date (YYYY-MM-DD): ");
                        LocalDate end = LocalDate.parse(sc.nextLine());

                        m.createGoal(name, target, end);
                    }
                    case 3 -> m.getDashBoard();
                    case 4 -> accountSettings(m);
                    case 5 -> {
                        System.out.println("Logged out.");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        }

        //+=============================+TRAINER OPERATIONS+=======================================||
        private static void trainerLogin() {
            System.out.print("\nEnter trainer account ID: ");
            int id = readInt();

            TrainerDAO t = new TrainerDAO();

            if (t.signIn(id) == null || !"trainer".equals(t.getRole())) {
                System.out.println("Invalid trainer ID.");
                return;
            }

            trainerMenu(t);
        }

        private static void trainerMenu(TrainerDAO t) {
            System.out.println("\nWelcome, Trainer " + t.getFullName());

            while (true) {
                System.out.println("\n--- Trainer Menu ---");
                System.out.println("1. Lookup Member");
                System.out.println("2. View Assignments");
                System.out.println("3. Settings");
                System.out.println("4. Logout");
                System.out.print("Choose: ");

                int choice = readInt();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Member first name: ");
                        String fn = sc.nextLine();
                        System.out.print("Member last name: ");
                        String ln = sc.nextLine();
                        t.lookupMember(fn, ln);
                    }
                    case 2 -> t.viewAssignments();
                    case 3 -> accountSettings(t);
                    case 4 -> {
                        System.out.println("Logged out.");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        }

        //+=============================+ADMIN OPERATIONS+=======================================||
        private static void adminLogin() {
            System.out.print("\nEnter admin account ID: ");
            int id = readInt();

            AdminDAO admin = new AdminDAO();

            if (admin.signIn(id) == null || !"admin".equals(admin.getRole())) {
                System.out.println("Invalid admin ID.");
                return;
            }

            adminMenu(admin);
        }

        private static void adminMenu(AdminDAO a) {
            System.out.println("\nWelcome, Admin " + a.getFullName());

            while (true) {
                System.out.println("\n--- Admin Menu ---");
                System.out.println("1. Add Trainer");
                System.out.println("2. Add Room");
                System.out.println("3. View rooms");
                System.out.println("4. Schedule Class");
                System.out.println("5. Settings");
                System.out.println("6. Logout");
                System.out.print("Choose: ");

                int choice = readInt();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Trainer first name: ");
                        String fn = sc.nextLine();
                        System.out.print("Trainer last name: ");
                        String ln = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Phone: ");
                        String phone = sc.nextLine();

                        int tid = a.addTrainer(fn, ln, email, phone);
                        System.out.println("Created trainer with ID: " + tid);
                    }

                    case 2 -> {
                        System.out.print("Room title: ");
                        String title = sc.nextLine();
                        System.out.print("Capacity: ");
                        int cap = readInt();
                        int rid = a.addRoom(title, cap);
                        System.out.println("Created room with ID: " + rid);
                    }
                    case 3 -> a.viewRooms();

                    case 4 -> {
                        System.out.print("Class type: ");
                        String type = sc.nextLine();
                        System.out.print("Class title: ");
                        String title = sc.nextLine();
                        System.out.print("Room ID: ");
                        int roomId = readInt();
                        System.out.print("Trainer ID: ");
                        int trainerId = readInt();
                        System.out.print("Class date (YYYY-MM-DD): ");
                        String date = sc.nextLine();
                        System.out.print("Start time (HH:MM): ");
                        String startTime = sc.nextLine();
                        System.out.print("End time (HH:MM): ");
                        String endTime = sc.nextLine();

                        String[] startParts = startTime.split(":");
                        String[] endParts = endTime.split(":");

                        if (startParts.length != 2 || endParts.length != 2) {
                            System.out.println("Invalid time format. Expected HH:MM");
                            return;
                        }

                        int sh = Integer.parseInt(startParts[0]);
                        int sm = Integer.parseInt(startParts[1]);
                        int eh = Integer.parseInt(endParts[0]);
                        int em = Integer.parseInt(endParts[1]);

                        String start = String.format("%s %02d:%02d:00", date, sh, sm);
                        String end   = String.format("%s %02d:%02d:00", date, eh, em);

                        a.scheduleClass(type, title, roomId, trainerId, Timestamp.valueOf(start), Timestamp.valueOf(end));
                    }

                    case 5 -> accountSettings(a);

                    case 6 -> {
                        System.out.println("Logged out.");
                        return;
                    }

                    default -> System.out.println("Invalid choice.");
                }
            }
        }

        //+=============================+UTILITY OPERATIONS+=======================================||
        private static void accountSettings(Account a) {
            System.out.println("\nWelcome " + a.getFullName());

            while (true) {
                System.out.println("\n--- Settings Menu ---");
                System.out.println("1. Change name");
                System.out.println("2. Change email");
                System.out.println("3. Change phone number");
                System.out.println("4. Back");
                System.out.print("Choose: ");

                int choice = readInt();

                switch (choice) {
                    case 1 -> {
                        System.out.print("New first name: ");
                        String fn = sc.nextLine();
                        System.out.print("New last name: ");
                        String ln = sc.nextLine();

                        if (a.changeName(fn,ln)) {
                            System.out.println("Changed name to: " + fn + " " + ln + ".");
                        } else {
                            System.out.println("Failed to change name.");
                        }
                    }

                    case 2 -> {
                        System.out.print("New email: ");
                        String email = sc.nextLine();

                        if (a.emailExists(email)) {
                            System.out.println("Email: " + email + " already exists.");
                        } else if (a.changeEmail(email)) {
                            System.out.println("Changed email to: " + email + ".");
                        } else {
                            System.out.println("Failed to change email.");
                        }
                    }

                    case 3 -> {
                        System.out.print("New phone number: ");
                        String pn = sc.nextLine();
                        if (a.changePhone(pn)) {
                            System.out.println("Changed phone number to: " + pn + ".");
                        } else {
                            System.out.println("Failed to change phone number.");
                        }
                    }

                    case 4 -> {
                        System.out.println("Exited account settings.");
                        return;
                    }

                    default -> System.out.println("Invalid choice.");
                }
            }
        }

        private static int readInt() {
            while (true) {
                try {
                    String line = sc.nextLine();
                    return Integer.parseInt(line);
                } catch (Exception e) {
                    System.out.print("Invalid number. Try again: ");
                }
            }
        }

        private static double readDouble() {
            while (true) {
                try {
                    String line = sc.nextLine();
                    return Double.parseDouble(line);
                } catch (Exception e) {
                    System.out.print("Invalid number. Try again: ");
                }
            }
        }
    }

