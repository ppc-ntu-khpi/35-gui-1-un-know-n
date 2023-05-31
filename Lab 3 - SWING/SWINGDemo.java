import com.mybank.data.DataSource;
import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Alexander 'Taurus' Babich
 */
public class SWINGDemo {
    
    private final JEditorPane log;
    private final JEditorPane reportLog;
    private final JButton show;
    private final JButton report;
    private final JComboBox clients;
    
    public SWINGDemo() {
        log = new JEditorPane("text/html", "");
        log.setPreferredSize(new Dimension(250, 650));
        reportLog = new JEditorPane("text/html", "");
        show = new JButton("Show");
        report = new JButton("Report");
        clients = new JComboBox();
        for (int i=0; i<Bank.getNumberOfCustomers();i++)
        {
            clients.addItem(Bank.getCustomer(i).getLastName()+", "+Bank.getCustomer(i).getFirstName());
        }
        
    }
    
    private void launchFrame() {
        JFrame frame = new JFrame("MyBank clients");
        frame.setLayout(new BorderLayout());
        JPanel cpane = new JPanel();
        cpane.setLayout(new GridLayout(1, 2));
        
        cpane.add(clients);
        cpane.add(show);
        cpane.add(report);
        frame.add(cpane, BorderLayout.NORTH);
        frame.add(log, BorderLayout.CENTER);
        frame.add(reportLog, BorderLayout.AFTER_LAST_LINE);
        
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer current = Bank.getCustomer(clients.getSelectedIndex());
                String custInfo = "<br>&nbsp;<b><span style=\"font-size:2em;\">"+current.getLastName()+", "+
                        current.getFirstName()+"</span><br><hr>";
                for(int i = 0; i < current.getNumberOfAccounts(); i++) {
                    String accType = current.getAccount(i)instanceof CheckingAccount?"Checking":"Savings";
                    custInfo += "<br>&nbsp;<b>Acc Type: </b>"+accType+
                            "<br>&nbsp;<b>Balance: <span style=\"color:red;\">$"+current.getAccount(i).getBalance()+"</span></b>";
                }
                log.setText(custInfo);

            }
        });

        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String report = "&nbsp;<span style=\"font-size:1.5em;\"><b>CUSTOMERS REPORT</b></span>";
                for(int i = 0; i < Bank.getNumberOfCustomers(); i++) {
                    Customer customer = Bank.getCustomer(i);
                    report += "<hr><b>&nbsp;<span style=\"font-size:1em;\">"+customer.getLastName()+", "+customer.getFirstName()+"</span></b><br>";
                    for(int j = 0; j < customer.getNumberOfAccounts(); j++) {
                        String accType = customer.getAccount(j)instanceof CheckingAccount?"Checking":"Savings";
                        report += "&nbsp;<b>Acc Type: </b>"+accType+
                                "<br>&nbsp;<b>Balance: <span style=\"color:red;\">$"+customer.getAccount(j).getBalance()+"<br></span></b><br>";
                    }
                }
                reportLog.setText(report);
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setResizable(false);
        frame.setVisible(true);        
    }
    
    public static void main(String[] args) throws IOException {
        DataSource data = new DataSource("D:\\PPK\\OOP\\TUIdemo2\\data\\test.dat");
        data.loadData();

        SWINGDemo demo = new SWINGDemo();        
        demo.launchFrame();
    }
    
}
