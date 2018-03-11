/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.theController;
import designchallengemvc.Event;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
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
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Tadhg
 */
public class theView {

    private ArrayList<Event> events = new ArrayList<>(); //events

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
    public JButton doneBtnAgenda = new JButton("Done");

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

    public void attach(theController controller) {
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
	agendaTable.setDefaultRenderer(dayTable.getColumnClass(0), new AgendaDayRenderer(events, self));
        dayTable.setDefaultRenderer(dayTable.getColumnClass(0), new DayAgendaRenderer(events, self));		
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
                for (int i = 0; i < 48; i++) {
                    dayTable.setValueAt("", i, 1);
                }

                //clearning the agendas whenever you click on a different day
                int rowCount = agendaModel.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    agendaModel.removeRow(i);
                }

                //When you click on a day in calendar, it's gonna show the events & to-do tasks on that day
                for (int i = 0; i < events.size(); i++) {
                    if (events.get(i).month == controller.MonthToInt(monthLabel.getText()) && events.get(i).day == Integer.parseInt(parts[0]) && events.get(i).year == Integer.parseInt(cmbYear.getSelectedItem().toString())) {
                        dayTable.setValueAt(events.get(i).name, controller.TimeToRowNumber(events.get(i).startTime), 1);
                    }
                }

                YearInputLabel.setText("Year: " + cmbYear.getSelectedItem());
                MonthInputLabel.setText("Month: " + monthLabel.getText());
                DayInputLabel.setText("Day: " + parts[0]);
                dateLabel.setText(monthLabel.getText() + " " + parts[0] + ", " + cmbYear.getSelectedItem());

                arrangeAgenda();
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
        cmbYear.addActionListener(new cmbYear_Action());
        //added
        addBtnEvent.addActionListener(new btnAddEvent_Action());
        deleteBtnEvent.addActionListener(new btnDeleteEvent_Action());
        doneBtnAgenda.addActionListener(new btnDoneAgenda_Action());
        isDay.addActionListener(new isDay_Action());
        isAgenda.addActionListener(new isAgenda_Action());
        isTask.addActionListener(new isTask_Action());

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
        agendaTab.add(doneBtnAgenda);

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
        doneBtnAgenda.setBounds(100, 525, 80, 25);

        btnPrev.setBounds(9, 25, 50, 25);
        btnNext.setBounds(281, 25, 50, 25);
        scrollCalendarTable.setBounds(10, 50, 320, 250); //this is the calendar itself
        dayScroll.setBounds(10, 25, 700, 550);
        agendaScroll.setBounds(10, 25, 700, 500); //550 originally

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
            //agendaTable.setValueAt(time, i, 0);

            minute += 30;
            if (minute / 30 == 2) {
                minute = 0;
                hour++;
            }
        }

        refreshCalendar(monthBound, yearBound); //Refresh calendar
    }

    private int timeToInt(String time) {

        int result = Integer.parseInt(time.split(":")[0] + "" + time.split(":")[1]);

        return result;
    }

    private boolean conflicts(String start, String end) {

        int iStart = timeToInt(start);
        int iEnd = timeToInt(end);
        int check = 0, count = 0;

        for (int i = 0; i < events.size(); i++) {
            int eStart = timeToInt(events.get(i).startTime);
            int eEnd = timeToInt(events.get(i).endTime);

            if (Integer.parseInt(YearInputLabel.getText().split(": ")[1]) == events.get(i).year && controller.MonthToInt(MonthInputLabel.getText().split(": ")[1]) == events.get(i).month && Integer.parseInt(DayInputLabel.getText().split(": ")[1]) == events.get(i).day) {
                System.out.println(events.get(i).toString());
                System.out.println("checking using these: \n");

                System.out.println("iStart: " + iStart);
                System.out.println("iEnd: " + iEnd);
                System.out.println("eStart" + eStart);
                System.out.println("eEnd" + eEnd);

                count++;

                //start is inbetween estart and eend || start is equal to e start || end is inbetween || end is equal || start is before start and end is after end
                if (!(iStart >= eStart && iStart < eEnd || iStart == eStart || iEnd > eStart && iEnd <= eEnd || iStart <= eStart && iEnd >= eEnd)) {
                    check++;
                }
            }
        }

        if (check == count) {
            System.out.println("valid time");
            return true;
        } else {
            System.out.println("invalid time");
            return false;
        }
    }

    public void arrangeAgenda() { 
        ArrayList<Event> arrTemp = new ArrayList<>();

        for (int i = 0; i < events.size(); i++) {
            if (Integer.parseInt(YearInputLabel.getText().split(": ")[1]) == events.get(i).year && controller.MonthToInt(MonthInputLabel.getText().split(": ")[1]) == events.get(i).month && Integer.parseInt(DayInputLabel.getText().split(": ")[1]) == events.get(i).day) {
                arrTemp.add(events.get(i));
            }
        }

        if (!arrTemp.isEmpty()) {
            Event eTemp;
            for (int i = 1; i < arrTemp.size(); i++) {
                for (int j = i; j > 0; j--) {
                    if (timeToInt(arrTemp.get(j).startTime) < timeToInt(arrTemp.get(j - 1).startTime)) {
                        eTemp = arrTemp.get(j);
                        arrTemp.set(j, arrTemp.get(j - 1));
                        arrTemp.set(j - 1, eTemp);
                    }
                }
            }
        }

        for (int i = 0; i < arrTemp.size(); i++) {
            if (Integer.parseInt(YearInputLabel.getText().split(": ")[1]) == arrTemp.get(i).year && controller.MonthToInt(MonthInputLabel.getText().split(": ")[1]) == arrTemp.get(i).month && Integer.parseInt(DayInputLabel.getText().split(": ")[1]) == arrTemp.get(i).day) {
                if (arrTemp.get(i).color.equalsIgnoreCase("green")) {
                        agendaModel.addRow(new Object[]{arrTemp.get(i).startTime, arrTemp.get(i).name});
                    } else {
                        agendaModel.addRow(new Object[]{arrTemp.get(i).startTime + " - " + arrTemp.get(i).endTime, arrTemp.get(i).name});
                    }
                }
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

                String strStart = startTime.getSelectedItem().toString();
                String strEnd = endTime.getSelectedItem().toString();

                int iStart = timeToInt(strStart);
                int iEnd = timeToInt(strEnd);

                if (YearInputLabel.getText() != null && MonthInputLabel.getText() != null && DayInputLabel.getText() != null && iStart <= iEnd && conflicts(strStart, strEnd)) { //you have to add months to this because it checks all events not just events of the specific day
                    System.out.println("in");
                    int col = calendarTable.getSelectedColumn();
                    int row = calendarTable.getSelectedRow();

                    modelCalendarTable.setValueAt(modelCalendarTable.getValueAt(row, col) + "\u25EF ", row, col);

                    int month; //added until

                    String[] MonthParts = MonthInputLabel.getText().split(" ");

                    month = controller.MonthToInt(MonthParts[1]);

                    String[] parts = DayInputLabel.getText().split(" ");
                    String[] parts2 = YearInputLabel.getText().split(" ");

                    String date = month + "/" + parts[1] + "/" + parts2[1];

                    System.out.println(parts[1]);
                    System.out.println(parts2[1]);
                    System.out.println(month);

                    //what i'm planning to do is for the to-do tasks, we make a function that passes the start time, and that function "adds" 30 mins
                    if (isTask.isSelected()) {
                        events.add(new Event(date, eventBox.getText(), "Green", startTime.getSelectedItem().toString(), startTime.getSelectedItem().toString())); //start time and end time for tasks are the same, we can change it if we need it to be + 30 minutes but i think it should be fine
                        for (int i = 0; i < events.size(); i++) {
                            if (events.get(i).name.equals(eventBox.getText())) {
                                agendaModel.addRow(new Object[]{events.get(i).startTime, events.get(i).name});
                            }
                        }
                    } else {
                        events.add(new Event(date, eventBox.getText(), "Blue", startTime.getSelectedItem().toString(), endTime.getSelectedItem().toString()));
                        for (int i = 0; i < events.size(); i++) {
                            if (events.get(i).name.equals(eventBox.getText())) {
                                agendaModel.addRow(new Object[]{events.get(i).startTime, events.get(i).name});
                            }
                        }
                    }

                    controller.writeData(events, false);

                    dayTable.setValueAt(eventBox.getText(), controller.TimeToRowNumber(startTime.getSelectedItem().toString()), 1);
                    eventBox.setText("");

                }
            }
        }

        class btnDeleteEvent_Action implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked delete");
                int col = dayTable.getSelectedColumn();
                int row = dayTable.getSelectedRow();
                String name = dayTable.getValueAt(dayTable.getSelectedRow(), dayTable.getSelectedColumn()).toString();
                System.out.println(dayTable.getValueAt(dayTable.getSelectedRow(), dayTable.getSelectedColumn()));

                for (int i = 0; i < events.size(); i++) {
                    if (events.get(i).name.equals(name)) {
                        agendaModel.removeRow(i);
                    }
                }

                controller.deleteData(events, name);
                dayTable.setValueAt("", row, col);

            }

        }

        class btnDoneAgenda_Action implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                int col = agendaTable.getSelectedColumn();
                int row = agendaTable.getSelectedRow();
                String name = agendaTable.getValueAt(row, col).toString();

                for (int i = 0; i < events.size(); i++) {

                    if (events.get(i).name.equals(name) && events.get(i).color.equalsIgnoreCase("green")) {
                        controller.deleteData(events, name);
                        agendaTable.setValueAt(name + " (DONE)", row, col);
                    } else {
                        System.out.println("Does not work for events");
                    }
                }
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

        class isTask_Action implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isTask.isSelected()) {
                    endTime.setEnabled(false);
                } else {
                    endTime.setEnabled(true);
                }
            }

        }

    

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

}
