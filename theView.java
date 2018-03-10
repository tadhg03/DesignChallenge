/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallengemvc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
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
    public theController controller;

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
    public JLabel YearInputLabel, MonthInputLabel, DayInputLabel; // for adding
    public JLabel dateLabel; //for info
    public JButton addBtnEvent = new JButton("Add");
    public JCheckBox isTask;
    public JLabel sTimeLabel; //start time label
    public JLabel eTimeLabel; //end time label
    public JComboBox startTime, endTime; //fixing positions
    public JToggleButton isDay, isAgenda;
    public JButton deleteBtnEvent = new JButton("Delete");

    public JLabel nameEvent; //name of event/to-do item

    public JLabel isToDoList; //label for to-do list

    public JTable dayTable; //main day table
    public DefaultTableModel dayModel = new DefaultTableModel();
    public JScrollPane dayScroll;

    public JTable agendaTable;
    public DefaultTableModel agendaModel = new DefaultTableModel();
    public JScrollPane agendaScroll;

    /**
     * ** Calendar Table Components **
     */
    public JTable calendarTable;
    public DefaultTableModel modelCalendarTable;
    
    public void attach(theController controller){
        this.controller = controller;
    }

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
            modelCalendarTable.setValueAt(i + "\n", row, column);

        }

        for (i = 0; i < 6; i++) { //for checking if days are the same
            for (j = 0; j < 7; j++) {
                if (modelCalendarTable.getValueAt(i, j) != null) {
                    String val = modelCalendarTable.getValueAt(i, j).toString().split("\n")[0];
                    for (int k = 0; k < events.size(); k++) {
                        if (Integer.parseInt(val) == events.get(k).day && month + 1 == events.get(k).month && year == events.get(k).year) {
                            modelCalendarTable.setValueAt(modelCalendarTable.getValueAt(i, j) + "\u25EF ", i, j); //change to not edit the calendar
                        }
                    }
                }
            }
        }

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer(events));
        //agendaTable.setDefaultRenderer(agendaTable.getColumnClass(0), new DayAgendaRenderer(events));
        dayTable.setDefaultRenderer(dayTable.getColumnClass(0), new DayAgendaRenderer(events));
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
        YearInputLabel = new JLabel("Year:");
        MonthInputLabel = new JLabel("Month:");
        DayInputLabel = new JLabel("Day:");
        dateLabel = new JLabel("Today"); //fix later if you want to
        isTask = new JCheckBox();
        isDay = new JToggleButton("Day");
        isAgenda = new JToggleButton("Agenda");
        startTime = new JComboBox();
        endTime = new JComboBox();
        pName = new JLabel("Productivity Tool");
        sTimeLabel = new JLabel("Start Time:");
        eTimeLabel = new JLabel("End Time:");
        nameEvent = new JLabel("Event Name:");
        isToDoList = new JLabel("To-Do Task:");
        dayTable = new JTable(dayModel);
        agendaTable = new JTable(agendaModel);

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
                String[] parts = modelCalendarTable.getValueAt(row, col).toString().split("\n");
                //System.out.println(col + ", " + row);
                
                //Clearing the events & to-do tasks whenever you click on a different day
                for(int i = 0; i < 48; i++){
                dayTable.setValueAt("", i, 1);
                }
                
                //When you click on a day in calendar, it's gonna show the events & to-do tasks on that day
                for (int i = 0; i < events.size(); i++) {
                    if(events.get(i).month == MonthToInt(monthLabel.getText()) && events.get(i).day == Integer.parseInt(parts[0]) && events.get(i).year == Integer.parseInt(cmbYear.getSelectedItem().toString())){
                        //Events only displays in Day, not Agenda
                        if(events.get(i).color.equals("Blue"))    
                            dayTable.setValueAt(events.get(i).name ,TimeToRowNumber(events.get(i).startTime) ,1);
                        //To-Do Tasks shows up in both. Coloring is the only problem we have now
                        else{
                            dayTable.setValueAt(events.get(i).name ,TimeToRowNumber(events.get(i).startTime) ,1);
                            agendaTable.setValueAt(events.get(i).name ,TimeToRowNumber(events.get(i).startTime) ,1);
                        }
                    }
                }

                YearInputLabel.setText("Year: " + cmbYear.getSelectedItem());
                MonthInputLabel.setText("Month: " + monthLabel.getText());
                DayInputLabel.setText("Day: " + parts[0]);
                dateLabel.setText(monthLabel.getText() + " " + parts[0] + ", " + cmbYear.getSelectedItem());

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
        dayScroll = new JScrollPane(dayTable);
        agendaScroll = new JScrollPane(agendaTable);
        agendaTab.setVisible(false); //initially only the day is shown

        calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
        //added
        info.setBorder(BorderFactory.createTitledBorder("Information"));
        dayTab.setBorder(BorderFactory.createTitledBorder("Day"));
        agendaTab.setBorder(BorderFactory.createTitledBorder("Agenda"));
        addTab.setBorder(BorderFactory.createTitledBorder("Add"));

        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        addBtnEvent.addActionListener(new btnAddEvent_Action());
        deleteBtnEvent.addActionListener(new btnDeleteEvent_Action());
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
        calendarPanel.add(isToDoList);
        calendarPanel.add(YearInputLabel);
        calendarPanel.add(MonthInputLabel);
        calendarPanel.add(DayInputLabel);
        calendarPanel.add(addBtnEvent);
        calendarPanel.add(deleteBtnEvent);
        calendarPanel.add(btnPrev);
        calendarPanel.add(btnNext);
        calendarPanel.add(scrollCalendarTable);

        //info
        info.add(isDay);
        info.add(isAgenda);
        info.add(pName);
        info.add(dateLabel);

        //day
        dayTab.add(dayScroll);

        //agenda
        agendaTab.add(agendaScroll);

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
        eventBox.setBounds(10, 330, 150, 20);
        nameEvent.setBounds(10, 305, 90, 25);
        isToDoList.setBounds(10, 350, 90, 25);
        cmbYear.setBounds(460, 610, 160, 40);
        isTask.setBounds(85, 350, 90, 25);
        sTimeLabel.setBounds(180, 305, 90, 25);
        startTime.setBounds(250, 305, 80, 25);
        eTimeLabel.setBounds(188, 335, 90, 25);
        endTime.setBounds(250, 335, 80, 25);
        YearInputLabel.setBounds(250, 390, 80, 20);
        MonthInputLabel.setBounds(250, 410, 80, 20);
        DayInputLabel.setBounds(250, 430, 150, 20);
        addBtnEvent.setBounds(250, 365, 80, 25);
        deleteBtnEvent.setBounds(10, 380, 80, 25);
        
        btnPrev.setBounds(9, 25, 50, 25);
        btnNext.setBounds(281, 25, 50, 25);
        scrollCalendarTable.setBounds(10, 50, 320, 250); //this is the calendar itself
        dayScroll.setBounds(10, 25, 700, 550);
        agendaScroll.setBounds(10, 25, 700, 550);

        //setBounds info
        isDay.setBounds(850, 25, 100, 50);
        isAgenda.setBounds(950, 25, 100, 50);
        pName.setBounds(100, 25, 200, 50);
        pName.setFont(new Font("Calibri", Font.PLAIN, 20));
        dateLabel.setBounds(400, 25, 200, 50);
        dateLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

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
        dayModel.addColumn("Time");
        dayModel.addColumn("Events");

        agendaModel.addColumn("Time");
        agendaModel.addColumn("Tasks");

        calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

        calendarTable.getTableHeader().setResizingAllowed(false);
        calendarTable.getTableHeader().setReorderingAllowed(false);

        calendarTable.setColumnSelectionAllowed(true);
        calendarTable.setRowSelectionAllowed(true);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        calendarTable.setRowHeight(41);
        modelCalendarTable.setColumnCount(7);
        modelCalendarTable.setRowCount(6);

        dayTable.setRowHeight(41);
        dayModel.setColumnCount(2);
        dayModel.setRowCount(48);

        agendaTable.setRowHeight(41);
        agendaModel.setColumnCount(2);
        agendaModel.setRowCount(48);
        agendaTable.setShowGrid(true);

        for (int i = yearBound - 100; i <= yearBound + 100; i++) {
            cmbYear.addItem(String.valueOf(i));
        }

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
            dayTable.setValueAt(time, i, 0);
            agendaTable.setValueAt(time, i, 0);

            minute += 30;
            if (minute / 30 == 2) {
                minute = 0;
                hour++;
            }
        }

        refreshCalendar(monthBound, yearBound); //Refresh calendar
    }
    
    private int TimeToRowNumber(String eTime){
        
        switch(eTime){
            
            case "0:30":
                return 1;
                
            case "1:00":
                return 2;
                
            case "1:30":
                return 3;
                
            case "2:00":
                return 4;
            
            case "2:30":
                return 5;
                
            case "3:00":
                return 6;
                
            case "3:30":
                return 7;
                
            case "4:00":
                return 8;
                
            case "4:30":
                return 9;
                
            case "5:00":
                return 10;
                
            case "5:30":
                return 11;
                
            case "6:00":
                return 12;
            
            case "6:30":
                return 13;
                
            case "7:00":
                return 14;
                
            case "7:30":
                return 15;
                
            case "8:00":
                return 16;
                
            case "8:30":
                return 17;
                
            case "9:00":
                return 18;
                
            case "9:30":
                return 19;
                
            case "10:00":
                return 20;
            
            case "10:30":
                return 21;
                
            case "11:00":
                return 22;
                
            case "11:30":
                return 23;
                
            case "12:00":
                return 24;
                
            case "12:30":
                return 25;
                
            case "13:00":
                return 26;
                
            case "13:30":
                return 27;
                
            case "14:00":
                return 28;
            
            case "14:30":
                return 29;
                
            case "15:00":
                return 30;
                
            case "15:30":
                return 31;
                
            case "16:00":
                return 32;
                
            case "16:30":
                return 33;
                
            case "17:00":
                return 34;
                
            case "17:30":
                return 35;
                
            case "18:00":
                return 36;
            
            case "18:30":
                return 37;
                
            case "19:00":
                return 38;
                
            case "19:30":
                return 39;
                
            case "20:00":
                return 40;
                
            case "20:30":
                return 41;
                
            case "21:00":
                return 42;
                
            case "21:30":
                return 43;
                
            case "22:00":
                return 44;
            
            case "22:30":
                return 45;
                
            case "23:00":
                return 46;
                
            case "23:30":
                return 47;    
            
            //if time is 0:00    
            default:
                return 0;
        }
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

            System.out.println(YearInputLabel.getText());
            System.out.println(MonthInputLabel.getText());
            System.out.println(DayInputLabel.getText());
            if (YearInputLabel.getText() != null && MonthInputLabel.getText() != null && DayInputLabel.getText() != null) {
                System.out.println("in");
                int col = calendarTable.getSelectedColumn();
                int row = calendarTable.getSelectedRow();

                modelCalendarTable.setValueAt(modelCalendarTable.getValueAt(row, col) + "\u25EF ", row, col);

                int month; //added until

                String[] MonthParts = MonthInputLabel.getText().split(" ");

                month = MonthToInt(MonthParts[1]);

                String[] parts = DayInputLabel.getText().split(" ");
                String[] parts2 = YearInputLabel.getText().split(" ");

                String date = month + "/" + parts[1] + "/" + parts2[1];

                System.out.println(parts[1]);
                System.out.println(parts2[1]);
                System.out.println(month);
                
                //what i'm planning to do is for the to-do tasks, we make a function that passes the start time, and that function "adds" 30 mins
                
                if (isTask.isSelected()) {
                    events.add(new Event(date, eventBox.getText(), "Green", startTime.getSelectedItem().toString(), endTime.getSelectedItem().toString())); //add drop box first for start and end time
                } else {
                    events.add(new Event(date, eventBox.getText(), "Blue", startTime.getSelectedItem().toString(), endTime.getSelectedItem().toString())); //add drop box first for start and end time
                }
                
                controller.writeData(events, false);
                
                dayTable.setValueAt(eventBox.getText(), TimeToRowNumber(startTime.getSelectedItem().toString()), 1);
                eventBox.setText("");
            }
        }
    }
    
    class btnDeleteEvent_Action implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("clicked delete");
            int col = dayTable.getSelectedColumn();
            int row = dayTable.getSelectedRow();
            System.out.println(dayTable.getValueAt(dayTable.getSelectedRow(), dayTable.getSelectedColumn()));
            
            controller.deleteData(events, dayTable.getValueAt(dayTable.getSelectedRow(), dayTable.getSelectedColumn()).toString());
            dayTable.setValueAt("", row, col);
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
