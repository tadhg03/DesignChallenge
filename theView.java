/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallengemvc;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tadhg
 */
public class theView {

    private ArrayList<Event> events = new ArrayList<>();
    
    //self
    public theView self;

    /**
     * ** Day Components ***
     */
    public int yearBound, monthBound, dayBound, yearToday, monthToday;

    /**
     * ** Swing Components ***
     */
    public JLabel monthLabel, yearLabel;
    public JButton btnPrev, btnNext;
    public JComboBox cmbYear;
    public JFrame frmMain;
    public Container pane;
    public JScrollPane scrollCalendarTable;
    public JPanel calendarPanel;
    
    //What we added
    public JPanel info;
    public JPanel dayTab; //not done yet, uncomment when done
    public JPanel agendaTab; //not done yet, uncomment when done
    public JPanel addTab; //not done yet, uncomment when done
    public JLabel eventLabel, pName; //pName is just name
    public JTextField eventBox = new JTextField();
    public JLabel cmbYearInput, cmbMonthInput, cmbDayInput;
    public JButton addBtnEvent = new JButton("Add");
    public JCheckBox isTask;
    public JLabel sTimeLabel; //start time label
    public JLabel eTimeLabel; //end time label
    public JComboBox startTime, endTime; //fixing positions
    public JToggleButton isDay, isAgenda;
    
    public JLabel nameEvent; //name of event/to-do item
    public JTextField eName = new JTextField(); //textfield for events
    public JButton aEvent; //add the event
    
    public JLabel isToDoList; //label for to-do list

    /**
     * ** Calendar Table Components **
     */
    public JTable calendarTable;
    public DefaultTableModel modelCalendarTable;

    public void refreshCalendar(int month, int year) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som, i, j;

        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (month == 0 && year <= yearBound - 10) {
            btnPrev.setEnabled(false);
        }
        if (month == 11 && year >= yearBound + 100) {
            btnNext.setEnabled(false);
        }

        monthLabel.setText(months[month]);
        monthLabel.setBounds(145, 15, 100, 50);

        cmbYear.setSelectedItem("" + year);

        for (i = 0; i < 6; i++) {
            for (j = 0; j < 7; j++) {
                modelCalendarTable.setValueAt(null, i, j);
            }
        }

        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); //nod is how many days there in a month
        //System.out.println("nod is: "+nod);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK); //som is the count of which day during the week the month starts
        //System.out.println("som is: "+som);

        for (i = 1; i <= nod; i++) {
            int row = new Integer((i + som - 2) / 7);
            int column = (i + som - 2) % 7;
            modelCalendarTable.setValueAt(i, row, column);

        }

        for (i = 0; i < 6; i++) { //for checking if days are the same
            for (j = 0; j < 7; j++) {
                if (modelCalendarTable.getValueAt(i, j) != null) {
                    String val = modelCalendarTable.getValueAt(i, j).toString();
                    for (int k = 0; k < events.size(); k++) {
                        if (Integer.parseInt(val) == events.get(k).day && month + 1 == events.get(k).month && year == events.get(k).year) {
                            modelCalendarTable.setValueAt(modelCalendarTable.getValueAt(i, j) + " \u26AC", i, j); //change to not edit the calendar
                        }
                    }
                }
            }
        }

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer(events));
    }

    public theView() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        self = this;

        frmMain = new JFrame("Calendar Application");
        frmMain.setSize(1068, 720); //I made it smaller ok cause i feel like it's too big hahaha
        pane = frmMain.getContentPane();
        pane.setLayout(null);
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        monthLabel = new JLabel("January");
        yearLabel = new JLabel("Change year:");
        eventLabel = new JLabel("Add event:");
        cmbYear = new JComboBox();

        //what we created
        cmbYearInput = new JLabel("Year:");
        cmbMonthInput = new JLabel("Month:");
        cmbDayInput = new JLabel("Day:");
        isTask = new JCheckBox();
        isDay = new JToggleButton("Day");
        isAgenda = new JToggleButton("Agena");
        startTime = new JComboBox();
        endTime = new JComboBox();
        pName = new JLabel("Calendar Program");
        sTimeLabel = new JLabel("Start Time:");
        eTimeLabel = new JLabel("End Time:");
        nameEvent = new JLabel("Event Name:");
        aEvent = new JButton("Add");
        isToDoList = new JLabel("To-Do Task:");

        //adding times to start and end
        int hour = 0, minute = 0;
        String time;
        for (int i = 0; i < 48; i++) {
            if (minute == 0) {
                time = hour + ":00";
            } else {
                time = hour + ":" + minute;
            }
            startTime.addItem(time);
            endTime.addItem(time);
            minute += 30;
            if (minute / 30 == 2) {
                minute = 0;
                hour++;
            }
        }

        btnPrev = new JButton("<<");
        btnNext = new JButton(">>");
        modelCalendarTable = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        calendarTable = new JTable(modelCalendarTable);
        calendarTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int col = calendarTable.getSelectedColumn();
                int row = calendarTable.getSelectedRow();
                String[] parts = modelCalendarTable.getValueAt(row, col).toString().split(" ");
                //System.out.println(col + ", " + row);

                cmbYearInput.setText("Year: " + cmbYear.getSelectedItem());
                cmbMonthInput.setText("Month: " + monthLabel.getText());
                cmbDayInput.setText("Day: " + parts[0]);

                int month; //added until

                month = MonthToInt(monthLabel.getText());

            }
        });

        scrollCalendarTable = new JScrollPane(calendarTable);
        calendarPanel = new JPanel(null);
        //added
        info = new JPanel(null);
        dayTab = new JPanel(null);
        agendaTab = new JPanel(null);
        addTab = new JPanel(null);
        
        calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
        //added
        info.setBorder(BorderFactory.createTitledBorder("Information"));
        dayTab.setBorder(BorderFactory.createTitledBorder("Day"));
        agendaTab.setBorder(BorderFactory.createTitledBorder("Agenda"));
        addTab.setBorder(BorderFactory.createTitledBorder("Add"));
        
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        addBtnEvent.addActionListener(new btnAddEvent_Action());
        cmbYear.addActionListener(new cmbYear_Action());
        //added
        isDay.addActionListener(new isDay_Action());
        isAgenda.addActionListener(new isAgenda_Action());

        //pane
        pane.add(calendarPanel);
        pane.add(dayTab);
        pane.add(agendaTab);
        pane.add(addTab);
        pane.add(info);
        
        //calendarPanel
        calendarPanel.add(monthLabel);
        calendarPanel.add(yearLabel);
        calendarPanel.add(eventLabel);
        calendarPanel.add(eventBox);
        calendarPanel.add(cmbYear);
        calendarPanel.add(isTask);
        calendarPanel.add(sTimeLabel);
        calendarPanel.add(eTimeLabel);
        calendarPanel.add(startTime);
        calendarPanel.add(endTime);
        calendarPanel.add(nameEvent);
        calendarPanel.add(eName);
        calendarPanel.add(aEvent);
        calendarPanel.add(isToDoList);
        calendarPanel.add(cmbYearInput);
        calendarPanel.add(cmbMonthInput);
        calendarPanel.add(cmbDayInput);
        calendarPanel.add(addBtnEvent);
        calendarPanel.add(btnPrev);
        calendarPanel.add(btnNext);
        calendarPanel.add(scrollCalendarTable);
        
        //info
        info.add(isDay);
        info.add(isAgenda);
        info.add(pName);
        
        //setBounds pane
        calendarPanel.setBounds(0, 100, 340, 580);
        info.setBounds(0, 0, 1060, 100);
        dayTab.setBounds(340, 100, 720, 580);
        agendaTab.setBounds(340, 100, 720, 580);
        addTab.setBounds(340, 100, 720, 580);
        
        //setBounds(x, y, width, height) CP
        monthLabel.setBounds(320 - monthLabel.getPreferredSize().width / 2, 50, 200, 50);
        yearLabel.setBounds(20, 610, 160, 40);
        eventLabel.setBounds(20, 625, 170, 50);
        eventBox.setBounds(90, 640, 150, 20);
        nameEvent.setBounds(10, 305, 90, 25);
        eName.setBounds(10, 330, 150, 20);
        isToDoList.setBounds(10, 350, 90, 25);
        cmbYear.setBounds(460, 610, 160, 40);
        isTask.setBounds(85, 350, 90, 25);
        sTimeLabel.setBounds(180, 305, 90, 25);
        startTime.setBounds(250, 305, 80, 25);
        eTimeLabel.setBounds(188, 335, 90, 25);
        aEvent.setBounds(250, 365, 80, 25);
        endTime.setBounds(250, 335, 80, 25);
        cmbYearInput.setBounds(250, 640, 80, 20);
        cmbMonthInput.setBounds(250, 660, 80, 20);
        cmbDayInput.setBounds(250, 680, 150, 20);
        addBtnEvent.setBounds(90, 660, 60, 45);
        btnPrev.setBounds(9, 25, 50, 25);
        btnNext.setBounds(281, 25, 50, 25);
        scrollCalendarTable.setBounds(10, 50, 320, 250); //this is the calendar itself
        
        //setBounds info
        isDay.setBounds(850, 25, 100, 50);
        isAgenda.setBounds(950, 25, 100, 50);
        pName.setBounds(100, 25, 200, 50);
        pName.setFont(new Font("Calibri", Font.PLAIN, 20));

        frmMain.setResizable(false);
        frmMain.setVisible(true);

        GregorianCalendar cal = new GregorianCalendar();
        dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        monthBound = cal.get(GregorianCalendar.MONTH);
        yearBound = cal.get(GregorianCalendar.YEAR);
        monthToday = monthBound;
        yearToday = yearBound;

        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i = 0; i < 7; i++) {
            modelCalendarTable.addColumn(headers[i]);
        }

        calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

        calendarTable.getTableHeader().setResizingAllowed(false);
        calendarTable.getTableHeader().setReorderingAllowed(false);

        calendarTable.setColumnSelectionAllowed(true);
        calendarTable.setRowSelectionAllowed(true);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        calendarTable.setRowHeight(41);
        modelCalendarTable.setColumnCount(7);
        modelCalendarTable.setRowCount(6);

        for (int i = yearBound - 100; i <= yearBound + 100; i++) {
            cmbYear.addItem(String.valueOf(i));
        }

        refreshCalendar(monthBound, yearBound); //Refresh calendar
    }

    private int MonthToInt(String month) {

        switch (month.toLowerCase()) {
            case "january":
                return 1;

            case "february":
                return 2;

            case "march":
                return 3;

            case "april":
                return 4;

            case "may":
                return 5;

            case "june":
                return 6;

            case "july":
                return 7;

            case "august":
                return 8;

            case "september":
                return 9;

            case "october":
                return 10;

            case "november":
                return 11;

            case "december":
                return 12;

            default:
                return 0;

        }

    }

    class btnPrev_Action implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (monthToday == 0) {
                monthToday = 11;
                yearToday -= 1;
            } else {
                monthToday -= 1;
            }
            refreshCalendar(monthToday, yearToday);
        }
    }

    class btnNext_Action implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (monthToday == 11) {
                monthToday = 0;
                yearToday += 1;
            } else {
                monthToday += 1;
            }
            refreshCalendar(monthToday, yearToday);
        }
    }

    class cmbYear_Action implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (cmbYear.getSelectedItem() != null) {
                String b = cmbYear.getSelectedItem().toString();
                yearToday = Integer.parseInt(b);
                refreshCalendar(monthToday, yearToday);
            }
        }
    }

    class btnAddEvent_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
    
    class isDay_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dayTab.setVisible(true);
            agendaTab.setVisible(false);
            addTab.setVisible(false);
            isAgenda.setSelected(false);
        }
        
    }
    
    class isAgenda_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dayTab.setVisible(false);
            agendaTab.setVisible(true);
            addTab.setVisible(false);
            isDay.setSelected(false);
        }
        
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

}
