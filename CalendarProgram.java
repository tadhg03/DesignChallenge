/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

/**
 *
 * @author Arturo III
 */

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarProgram{
        
        //events
        public ArrayList<Event> events = new ArrayList<>();
        
        //self
        public CalendarProgram self;
        
        //observer
        private ArrayList<Observer> obs = new ArrayList<>();
	
        /**** Day Components ****/
	public int yearBound, monthBound, dayBound, yearToday, monthToday;

        /**** Swing Components ****/
        public JLabel monthLabel, yearLabel;
	public JButton btnPrev, btnNext;
        public JComboBox cmbYear;
	public JFrame frmMain;
	public Container pane;
	public JScrollPane scrollCalendarTable;
	public JPanel calendarPanel;
        
        //What we added
        public JLabel eventLabel;
        public JTextField eventBox = new JTextField();
        public JLabel cmbYearInput, cmbMonthInput, cmbDayInput;
        public JButton addBtnEvent = new JButton("Add");
        public JComboBox addColor;
        public JLabel eventColor;
        public JCheckBox isHoliday; //added
        
        /**** Calendar Table Components ***/
	public JTable calendarTable;
        public DefaultTableModel modelCalendarTable;
        
        public void attatchObserver(Observer ob){
        
            obs.add(ob);
            notifyObs();
            
        }
        
        public void refreshCalendar(int month, int year)
        {
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som, i, j;
			
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= yearBound-10)
                    btnPrev.setEnabled(false);
		if (month == 11 && year >= yearBound+100)
                    btnNext.setEnabled(false);
                
		monthLabel.setText(months[month]);
		monthLabel.setBounds(320-monthLabel.getPreferredSize().width/2, 50, 360, 50);
                
		cmbYear.setSelectedItem(""+year);
		
		for (i = 0; i < 6; i++)
			for (j = 0; j < 7; j++)
				modelCalendarTable.setValueAt(null, i, j);
		
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); //nod is how many days there in a month
                //System.out.println("nod is: "+nod);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK); //som is the count of which day during the week the month starts
                //System.out.println("som is: "+som);
		
		for (i = 1; i <= nod; i++)
                {
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			modelCalendarTable.setValueAt(i, row, column);
                      
		}
                
                for(i = 0; i < 6; i++){ //for checking if days are the same
                    for(j = 0; j < 7; j++){
                        if(modelCalendarTable.getValueAt(i, j) != null){
                            String[] parts = modelCalendarTable.getValueAt(i, j).toString().split(" ");
                                for(int k = 0; k < events.size(); k++){
                                    if(events.get(k).holiday == false)
                                        if(Integer.parseInt(parts[0]) == events.get(k).day && month + 1 == events.get(k).month && year == events.get(k).year)
                                            modelCalendarTable.setValueAt(modelCalendarTable.getValueAt(i, j) + " " + events.get(k).name, i, j);
                                    if(events.get(k).holiday == true)
                                        if(Integer.parseInt(parts[0]) == events.get(k).day && month + 1 == events.get(k).month && year >= events.get(k).year)
                                            modelCalendarTable.setValueAt(modelCalendarTable.getValueAt(i, j) + " " + events.get(k).name, i, j);
                                }
                        }
                    }
                }
                notifyObs();
                
		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer(events));
	}
        
	public CalendarProgram()
        {
                
		try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
		catch (Exception e) {}
                
                self = this;
                
		frmMain = new JFrame ("Calendar Application");
                frmMain.setSize(660, 750);
		pane = frmMain.getContentPane();
		pane.setLayout(null);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		monthLabel = new JLabel ("January");
		yearLabel = new JLabel ("Change year:");
                eventLabel = new JLabel("Add event:");
		cmbYear = new JComboBox();
                
                //what we created
                cmbYearInput = new JLabel("Year:");
                cmbMonthInput = new JLabel("Month:");
                cmbDayInput = new JLabel("Day:");
                addColor = new JComboBox();
                isHoliday = new JCheckBox("Holiday"); //added
                
                //Adding colors to JComboBoc
                addColor.addItem("None");
                addColor.addItem("Red");
                addColor.addItem("Green");
                addColor.addItem("Blue");
                
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		modelCalendarTable = new DefaultTableModel()
                {
                    public boolean isCellEditable(int rowIndex, int mColIndex)
                    {
                        return false;
                    }
                };
                
		calendarTable = new JTable(modelCalendarTable);
                calendarTable.addMouseListener(new MouseAdapter()   
                {  
                    public void mouseClicked(MouseEvent evt)  
                    {  
                        int col = calendarTable.getSelectedColumn();  
                        int row = calendarTable.getSelectedRow();
                        String[] parts = modelCalendarTable.getValueAt(row, col).toString().split(" ");
                        //System.out.println(col + ", " + row);
                        
                        cmbYearInput.setText("Year: "+ cmbYear.getSelectedItem());
                        cmbMonthInput.setText("Month: " + monthLabel.getText());
                        cmbDayInput.setText("Day: " + parts[0]);
                        
                        int month; //added until
                        
                        month = MonthToInt(monthLabel.getText());
                        
                        
                        } 
                });
                
		scrollCalendarTable = new JScrollPane(calendarTable);
		calendarPanel = new JPanel(null);

		calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
		
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
                addBtnEvent.addActionListener(new btnAddEvent_Action());
		cmbYear.addActionListener(new cmbYear_Action());
		
		pane.add(calendarPanel);
		calendarPanel.add(monthLabel);
		calendarPanel.add(yearLabel);
                calendarPanel.add(eventLabel);
                calendarPanel.add(eventBox);
		calendarPanel.add(cmbYear);
                calendarPanel.add(addColor);
                calendarPanel.add(cmbYearInput);
                calendarPanel.add(cmbMonthInput);
                calendarPanel.add(cmbDayInput);
                calendarPanel.add(isHoliday); //added
                calendarPanel.add(addBtnEvent);
		calendarPanel.add(btnPrev);
		calendarPanel.add(btnNext);
		calendarPanel.add(scrollCalendarTable);
		
                calendarPanel.setBounds(0, 0, 650, 710);
                monthLabel.setBounds(320-monthLabel.getPreferredSize().width/2, 50, 200, 50);
		yearLabel.setBounds(20, 610, 160, 40);
                eventLabel.setBounds(20, 625, 170, 50);
                eventBox.setBounds(90, 640, 180, 60);
                eventBox.setSize(150, 20);
		cmbYear.setBounds(460, 610, 160, 40);
                addColor.setBounds(150, 660, 180, 60);
                addColor.setSize(90, 25);
                cmbYearInput.setBounds(250, 640, 180, 60);
                cmbMonthInput.setBounds(250, 660, 180, 60);
                cmbDayInput.setBounds(250, 680, 180, 60);
                cmbYearInput.setSize(80, 20);
                cmbDayInput.setSize(80, 20);
                cmbMonthInput.setSize(150, 20);
                isHoliday.setBounds(460, 650, 160, 40); //added
                addBtnEvent.setBounds(90, 660, 180, 60);
                addBtnEvent.setSize(60, 45);
		btnPrev.setBounds(20, 50, 100, 50);
		btnNext.setBounds(520, 50, 100, 50);
		scrollCalendarTable.setBounds(20, 100, 600, 500);
                
		frmMain.setResizable(false);
		frmMain.setVisible(true);
		
		GregorianCalendar cal = new GregorianCalendar();
		dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
		monthBound = cal.get(GregorianCalendar.MONTH);
		yearBound = cal.get(GregorianCalendar.YEAR);
		monthToday = monthBound; 
		yearToday = yearBound;
		
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
			modelCalendarTable.addColumn(headers[i]);
		}
		
		calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		calendarTable.setRowHeight(76);
		modelCalendarTable.setColumnCount(7);
		modelCalendarTable.setRowCount(6);
		
		for (int i = yearBound-100; i <= yearBound+100; i++)
                {
			cmbYear.addItem(String.valueOf(i));
		}
                
                //initializing the events.
                initEvents();
		
		refreshCalendar (monthBound, yearBound); //Refresh calendar
	}

    private void initEvents() {
        int temp;//for day
        CSVDCParser csvDataParser = new CSVDCParser(this);
        PSVDCParser psvDataParser = new PSVDCParser(this);
        
        //opens Philippine Holidays file and DLSU Unicalendar to get the holiday and uni data
        csvDataParser.readData("Philippine Holidays");
        psvDataParser.readData("DLSU Unicalendar");
        
        csvDataParser.processData(this);
        psvDataParser.processData(this);
        
        //since you're making a new event
        
        for(int i = 0; i < events.size(); i++){
            int month = events.get(i).month;
            int year = events.get(i).year;
            //System.out.println(events.get(i).month+"   "+ events.get(i).year);
            refreshCalendar(month - 1,year);
        }
        
    }
    
    private int MonthToInt(String month){
        
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
                return  7;
                
            case "august":
                return  8;
                
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
    
    public void notifyObs(){
        Calendar e = Calendar.getInstance();
        int temp = e.get(Calendar.MONTH) + 1;
        
        int i, j;
        
        for(i = 0; i < events.size(); i++){
            if(events.get(i).day == e.get(Calendar.DAY_OF_MONTH) && events.get(i).month == temp && events.get(i).year == e.get(Calendar.YEAR)){
                for(j = 0; j < obs.size(); j++){
                    obs.get(j).update(events.get(i));
                }
            }
            else if(events.get(i).day == e.get(Calendar.DAY_OF_MONTH) && events.get(i).month == temp && events.get(i).year <= e.get(Calendar.YEAR) && events.get(i).holiday == true){
                for(j = 0; j < obs.size(); j++){
                    obs.get(j).update(events.get(i));
                }
            }    
        }
    }
    
	class btnPrev_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (monthToday == 0)
                        {
				monthToday = 11;
				yearToday -= 1;
			}
			else
                        {
				monthToday -= 1;
			}
			refreshCalendar(monthToday, yearToday);
		}
	}
	class btnNext_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (monthToday == 11)
                        {
				monthToday = 0;
				yearToday += 1;
			}
			else
                        {
				monthToday += 1;
			}
			refreshCalendar(monthToday, yearToday);
		}
	}
	class cmbYear_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (cmbYear.getSelectedItem() != null)
                        {
				String b = cmbYear.getSelectedItem().toString();
				yearToday = Integer.parseInt(b);
				refreshCalendar(monthToday, yearToday);
			}
		}
	}
        class btnAddEvent_Action implements ActionListener
        {
            public void actionPerformed (ActionEvent e)
            {
                String filename = "";
                
                if(cmbYearInput.getText() != null && cmbMonthInput.getText() != null && cmbDayInput.getText() != null){
                    int col = calendarTable.getSelectedColumn();  
                    int row = calendarTable.getSelectedRow();

                    modelCalendarTable.setValueAt(modelCalendarTable.getValueAt(row, col) + " " + eventBox.getText(), row, col);
                    
                    int month; //added until
                    
                    String[] MonthParts = cmbMonthInput.getText().split(" ");
                    
                    month = MonthToInt(MonthParts[1]);
                     
                    String[] parts = cmbDayInput.getText().split(" ");
                    String[] parts2 = cmbYearInput.getText().split(" ");
                    
                    
                    String date = month + "/" +parts[1]+ "/" +parts2[1];
                    
                    System.out.println(parts[1]);
                    System.out.println(parts2[1]);
                    System.out.println(month);

                    if(isHoliday.isSelected())
                        events.add(new Event(date, eventBox.getText(), addColor.getSelectedItem().toString(), true)); //still dont have holiday input
                    else
                        events.add(new Event(date, eventBox.getText(), addColor.getSelectedItem().toString(), false)); //still dont have holiday input
                    
                    CSVDCParser csvDataParser = new CSVDCParser(self);
                    PSVDCParser psvDataParser = new PSVDCParser(self);
                    
                    csvDataParser.writeData(events);
                    psvDataParser.writeData(events);
                    
                    notifyObs();
                    
                }
            }
        }
}
