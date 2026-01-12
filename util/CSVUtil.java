package util;

import model.Transaction;
import java.io.FileWriter;
import java.util.List;

public class CSVUtil {

    public static void exportTransactionsToCSV(List<Transaction> list, String fileName) {

        try (FileWriter writer = new FileWriter(fileName)) {

            // Header
            writer.append("TransactionID,AccountNo,Type,Amount,BalanceAfter,DateTime\n");

            for (Transaction t : list) {
                writer.append(t.getTxId() + ",");
                writer.append(t.getAccountNo() + ",");
                writer.append(t.getType() + ",");
                writer.append(t.getAmount() + ",");
                writer.append(t.getBalanceAfter() + ",");
                writer.append(t.getTimestamp() + "\n");
            }

            System.out.println("✅ Statement exported to " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
