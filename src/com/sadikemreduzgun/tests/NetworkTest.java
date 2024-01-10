package com.sadikemreduzgun.tests;

public class NetworkTest {

        public static void main(String[] args) {
            String[] usernames = {"joseph", "marek", "always a long name"};

            for (String username : usernames) {
                String formattedString = formatUsername(username, 25);
                System.out.println(formattedString);
            }
        }

        private static String formatUsername(String username, int width) {
            return String.format("%-" + width + "s %d", username, 3); // Assuming a fixed value (e.g., 12) for demonstration
        }
    }

