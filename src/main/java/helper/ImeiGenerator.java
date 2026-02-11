package helper;

public class ImeiGenerator {

    public static String generateIMEI() {

        StringBuilder imei = new StringBuilder();

        // 14 số đầu
        for (int i = 0; i < 14; i++) {
            imei.append((int) (Math.random() * 10));
        }

        // Tính checksum Luhn
        int sum = 0;
        for (int i = 0; i < 14; i++) {

            int digit = Character.getNumericValue(imei.charAt(i));

            if (i % 2 == 1) {
                digit *= 2;
                if (digit > 9)
                    digit -= 9;
            }

            sum += digit;
        }

        int checkDigit = (10 - (sum % 10)) % 10;

        imei.append(checkDigit);

        return imei.toString();
    }
}
