package aula2;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RandomNumbers
{
	JFrame m_window;
	JTextField m_jt_num1, m_jt_num2;
	JButton m_jb_stop;
	
	TGetNumber m_numGenerators[];
	
	private class TGetNumber extends Thread
	{
		JTextField jt_number;
		int total_numbers;
		
		int min, max;
		long sleep_time;
		
		TGetNumber(JTextField text, int min, int max, long sleep_time)
		{
			jt_number = text;
			total_numbers = 0;
			
			this.min = min;
			this.max = max;
			this.sleep_time = sleep_time;
		}
		
		@Override
		public void run()
		{
			try
			{
				while(true)
				{
					int num = (int)(Math.random() * max - min) + min;
					total_numbers++;
					jt_number.setText("" + num);
					sleep(sleep_time);
				}
			}
			catch(InterruptedException e)
			{}
		}
	}
	
	RandomNumbers()
	{
		setup();
		
		m_numGenerators = new TGetNumber[2];
		
		m_numGenerators[0] = new TGetNumber(m_jt_num1, 1000, 9999, 0);
		m_numGenerators[1] = new TGetNumber(m_jt_num2, 1, 9, 500);
		
		for(int i = 0; i < m_numGenerators.length; i++)
		{
			m_numGenerators[i].start();
		}
		
		for(int i = 0; i < m_numGenerators.length; i++)
		{
			try{ m_numGenerators[i].join(); }
			catch(InterruptedException e){}
		}
	}
	
	private void setup()
	{
		m_window = new JFrame();
		m_window.setLayout(new GridLayout(2, 1));
		m_window.setLocation(new Point(300, 300));
		
		JPanel nums = new JPanel();
		
		m_jt_num1 = new JTextField("0");
		m_jt_num2 = new JTextField("0");
		
		nums.add(m_jt_num1);
		nums.add(m_jt_num2);
		
		m_jb_stop = new JButton("Stop");
		
		m_jb_stop.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					stopButton();
				}
			}
		);
		
		m_window.add(nums);
		m_window.add(m_jb_stop);
		
		m_window.pack();
		m_window.setVisible(true);
	}
	
	public void stopButton()
	{
		int total = 0;
		
		for(int i = 0; i < m_numGenerators.length; i++)
		{
			m_numGenerators[i].interrupt();
			total += m_numGenerators[i].total_numbers;
		}
		
		System.out.println("Total of Numbers Generated: " + total);
	}
	
	public static void main(String args[])
	{
		RandomNumbers r = new RandomNumbers();
	}
}
