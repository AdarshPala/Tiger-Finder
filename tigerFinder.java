/*
  Name: Adarsh Pala
  Date: May 19th 2014
  Purpose: Game for Oskara Morgenstern
*/

/*Picture Sources:

www.photosof.org, wallpaperology.com, minecraftdls1.blogspot.com, logos.wikia.com, www.getdownintwo.com, commons.wikimedia.org
http://jenntgrace.com/wp-content/uploads/2012/12/4.png, http://youthleaderstash.com/wp-content/uploads/2011/01/512px-5-skylt_Swedish_roadsign.svg_.png
http://upload.wikimedia.org/wikipedia/commons/b/be/Taiwan_Main_Road_6_Sign.gif, http://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/RO_Roadsign_7.svg/600px-RO_Roadsign_7.svg.png
http://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/8_ball_icon.svg/200px-8_ball_icon.svg.png, http://iphonewallpaperonline.com/wp-content/uploads/2011/07/A-Tigers-Stripes.jpg
http://bgfons.com/upload/leaves_texture4972.jpg, http://www.psdgraphics.com/file/mouse-cursor-icon.jpg, http://www.clker.com/cliparts/0/g/0/M/u/l/orange-flag-hi.png */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.Applet;
import java.applet.*;
;

public class tigerFinder extends Applet implements ActionListener
{
    Panel p_card;
    Panel card1, card2, card3, card4, card5, card6, card7;
    CardLayout cdLayout = new CardLayout ();
    JLabel ptsLabel; //the JLabel where the points are displayed
    int pts = 999; //points will be used to push the user to beat the game in the least number of moves
    int row = 15; //the grid is 15x15
    JButton a[] = new JButton [row * row]; //the grid where the user plays
    int numFlags = 100; //will change depending on the difficulty
    JLabel flagsLeft; //the JLabel to tell the user how many flags are left
    int n = 1; //count for the different modes (flag or default)
    int d = 1; //count for the difficulties (pie to suicide)
    int bombs[] [] = new int [row] [row]; //tracking array for the tigers
    int flags[] [] = new int [row] [row]; //tracking array for the flags
    int trackNum[] [] = new int [row] [row];  //tracking array for the numbers
    int bombX = 0; //"tiger" coordinate
    int bombY = 0; //"tiger" coordinate
    AudioClip soundFile; //ambient sound
    JLabel mode; //tells the user what mode he/she is in (flag or default)

    public void init ()
    {
	resize (750, 790);
	p_card = new Panel ();
	p_card.setLayout (cdLayout);
	titleScreen ();
	chooseScreen ();
	gameScreen ();
	helpScreen ();
	winScreen (pts);
	creditScreen ();
	loseScreen (pts);
	setLayout (new BorderLayout ());
	add ("Center", p_card);
    }


    public void titleScreen ()
    {
	card1 = new Panel ();
	soundFile = getAudioClip (getDocumentBase (), "forest.wav");
	soundFile.loop ();
	Panel p = new Panel ();
	card1.setBackground (Color.black);
	JLabel title = new JLabel ("Tiger Finder");
	title.setFont (new Font ("Papyrus", Font.BOLD, 90));
	title.setForeground (new Color (255, 128, 0));
	JLabel blank1 = new JLabel ("                                                                                                                   ");
	JButton play = new JButton ("     Play Game     ");
	play.setFont (new Font ("Chiller", Font.PLAIN, 35));
	play.setBackground (Color.black);
	play.setForeground (new Color (0, 200, 0));
	play.setActionCommand ("play");
	play.addActionListener (this);
	p.add (play);
	JButton help = new JButton ("      Help      ");
	help.setFont (new Font ("Chiller", Font.PLAIN, 35));
	help.setBackground (Color.black);
	help.setForeground (new Color (0, 200, 0));
	help.setActionCommand ("help");
	help.addActionListener (this);
	p.add (help);
	JButton quit = new JButton ("      Quit      ");
	quit.setFont (new Font ("Chiller", Font.PLAIN, 35));
	quit.setBackground (Color.black);
	quit.setForeground (new Color (0, 200, 0));
	quit.setActionCommand ("quit");
	quit.addActionListener (this);
	JLabel blank = new JLabel ("                                                                                                                   ");
	JLabel blank2 = new JLabel ("                                                                                                                   ");
	JLabel titleImg = new JLabel (createImageIcon ("tigerBackground7.jpg")); //photoshopped to blend with background
	card1.add (title);
	card1.add (p);
	card1.add (quit);
	card1.add (blank);
	card1.add (blank2);
	card1.add (blank1);
	card1.add (titleImg);
	p_card.add ("1", card1);
    }


    public void gameScreen ()
    {
	card2 = new Panel ();
	card2.setBackground (new Color (0, 66, 0));
	Dimension panelD = new Dimension (42, 42); //size of each square in the grid
	Panel p1 = new Panel (new GridLayout (row, row));
	Panel p2 = new Panel ();
	ptsLabel = new JLabel ("Points: " + pts);
	ptsLabel.setFont (new Font ("OCR A Extended", Font.BOLD, 35));
	ptsLabel.setForeground (Color.white);
	JLabel blank = new JLabel ("                                                                                                                   ");
	blank.setFont (new Font ("OCR A Extended", Font.PLAIN, 8));
	flagsLeft = new JLabel ("Flags Left: " + numFlags + "    ");
	flagsLeft.setFont (new Font ("OCR A Extended", Font.PLAIN, 20));
	flagsLeft.setForeground (Color.white);
	JButton default1 = new JButton ("");
	default1.addActionListener (this);
	default1.setBackground (new Color (0, 150, 0));
	default1.setActionCommand ("default");
	default1.setPreferredSize (panelD);
	default1.setIcon (createImageIcon ("click.jpg"));
	JButton flagOp = new JButton ("");
	flagOp.addActionListener (this);
	flagOp.setBackground (new Color (0, 150, 0));
	flagOp.setActionCommand ("flag");
	flagOp.setPreferredSize (panelD);
	flagOp.setIcon (createImageIcon ("flag.jpg"));
	mode = new JLabel ("   Mode: Default");
	mode.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	mode.setForeground (Color.white);
	for (int i = 0 ; i < a.length ; i++) //this for loop creates the grid with 
	{ //1D masqerading
	    a [i] = new JButton ("");
	    p1.add (a [i]);
	    a [i].addActionListener (this);
	    a [i].setBackground (Color.black);
	    a [i].setActionCommand ("" + i);
	    a [i].setPreferredSize (panelD);
	    a [i].setIcon (createImageIcon ("leafTexture.jpg"));
	}
	JButton titleQuit = new JButton ("Quit to Title");
	titleQuit.setFont (new Font ("Chiller", Font.PLAIN, 25));
	titleQuit.setBackground (Color.black);
	titleQuit.setForeground (new Color (255, 128, 0));
	titleQuit.setActionCommand ("titleQuit");
	titleQuit.addActionListener (this);
	p2.add (titleQuit);
	JButton reset = new JButton ("Reset");
	reset.setFont (new Font ("Chiller", Font.PLAIN, 25));
	reset.setBackground (Color.black);
	reset.setForeground (new Color (255, 128, 0));
	reset.setActionCommand ("reset");
	reset.addActionListener (this);
	p2.add (reset);
	JButton help = new JButton ("Help");
	help.setFont (new Font ("Chiller", Font.PLAIN, 25));
	help.setBackground (Color.black);
	help.setForeground (new Color (255, 128, 0));
	help.setActionCommand ("help");
	help.addActionListener (this);
	p2.add (help);
	card2.add (ptsLabel);
	card2.add (blank);
	card2.add (flagsLeft);
	card2.add (default1);
	card2.add (flagOp);
	card2.add (mode);
	card2.add (p1);
	card2.add (p2);
	p_card.add ("2", card2);
    }


    public void helpScreen ()
    {
	card3 = new Panel ();
	card3.setBackground (Color.black);
	Panel p = new Panel ();
	JLabel instruct = new JLabel ("The Mission");
	instruct.setFont (new Font ("OCR A Extended", Font.PLAIN, 50));
	instruct.setForeground (new Color (255, 128, 0));
	JLabel space = new JLabel ("--------------------------------------------------------------------------------");
	space.setForeground (new Color (255, 128, 0));
	space.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	JLabel i1 = new JLabel ("/*Your mission, should you choose to accept it, is to find the tigers that have");
	JLabel i2 = new JLabel ("escaped from a zoo before anyone is hurt. Find all of the tigers to win the game.");
	JLabel i3 = new JLabel ("Click on any green square to search the surrounding squares until a tiger is");
	JLabel i4 = new JLabel ("detected. The numbers that appears once you click on a green square indicates how");
	JLabel i5 = new JLabel ("many tigers are touching that square. If a number \"3\" appears for example, then");
	JLabel i6 = new JLabel ("you know that there are only 3 tigers touching that square (either vertically,");
	JLabel i7 = new JLabel ("horizontally or diagonally). If you think there is a tiger in a certain square");
	JLabel i8 = new JLabel ("then flag it by switching to the flag button. If all squares contianing tigers are");
	JLabel i9 = new JLabel ("flagged, you win. However, if you left click on a square containing a tiger you");
	JLabel i10 = new JLabel ("lose the game. Try to find all the tigers in the least possible number of moves");
	JLabel i11 = new JLabel ("because everytime you click on a square, your point score decreases by exactly 1.*/");
	JLabel spac2 = new JLabel ("                                                                               ");
	spac2.setForeground (new Color (255, 128, 0));
	spac2.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i1.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i2.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i3.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i4.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i5.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i6.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i7.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i8.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i9.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i10.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i11.setFont (new Font ("OCR A Extended", Font.PLAIN, 15));
	i1.setForeground (new Color (255, 128, 0));
	i2.setForeground (new Color (255, 128, 0));
	i3.setForeground (new Color (255, 128, 0));
	i4.setForeground (new Color (255, 128, 0));
	i5.setForeground (new Color (255, 128, 0));
	i6.setForeground (new Color (255, 128, 0));
	i7.setForeground (new Color (255, 128, 0));
	i8.setForeground (new Color (255, 128, 0));
	i9.setForeground (new Color (255, 128, 0));
	i10.setForeground (new Color (255, 128, 0));
	i11.setForeground (new Color (255, 128, 0));
	JLabel helpTiger = new JLabel (createImageIcon ("helpTiger2.jpg")); //photoshopped to blend with background
	JButton accept = new JButton ("Accept");
	accept.setFont (new Font ("OCR A Extended", Font.PLAIN, 40));
	accept.setBackground (Color.black);
	accept.setForeground (new Color (255, 128, 0));
	accept.setActionCommand ("play");
	accept.addActionListener (this);
	JButton decline = new JButton ("Decline");
	decline.setFont (new Font ("OCR A Extended", Font.PLAIN, 40));
	decline.setBackground (Color.black);
	decline.setForeground (new Color (255, 128, 0));
	decline.setActionCommand ("titleQuit");
	decline.addActionListener (this);
	card3.add (instruct);
	card3.add (space);
	card3.add (i1);
	card3.add (i2);
	card3.add (i3);
	card3.add (i4);
	card3.add (i5);
	card3.add (i6);
	card3.add (i7);
	card3.add (i8);
	card3.add (i9);
	card3.add (i10);
	card3.add (i11);
	card3.add (helpTiger);
	card3.add (spac2);
	p.add (accept);
	p.add (decline);
	card3.add (p);
	p_card.add ("3", card3);
    }


    public void winScreen (int pts)
    {
	card4 = new Panel ();
	JLabel winPic = new JLabel (createImageIcon ("screenWin2.jpg")); //pre-made picture
	JLabel score = new JLabel ("Score: " + pts);
	score.setFont (new Font ("OCR A Extended", Font.PLAIN, 60));
	score.setForeground (new Color (255, 128, 0));
	JLabel blank = new JLabel ("                                                                                                                   ");
	blank.setFont (new Font ("OCR A Extended", Font.PLAIN, 30));
	JLabel blank2 = new JLabel ("                                                                                                                   ");
	blank2.setFont (new Font ("OCR A Extended", Font.PLAIN, 30));
	JButton again = new JButton ("Play again");
	again.setFont (new Font ("Chiller", Font.PLAIN, 45));
	again.setBackground (Color.black);
	again.setForeground (new Color (255, 128, 0));
	again.setActionCommand ("reset");
	again.addActionListener (this);
	JButton titleQuit = new JButton ("Quit to Title");
	titleQuit.setFont (new Font ("Chiller", Font.PLAIN, 45));
	titleQuit.setBackground (Color.black);
	titleQuit.setForeground (new Color (255, 128, 0));
	titleQuit.setActionCommand ("titleQuit");
	titleQuit.addActionListener (this);
	JButton credits = new JButton ("Credits");
	credits.setFont (new Font ("Chiller", Font.PLAIN, 45));
	credits.setBackground (Color.black);
	credits.setForeground (new Color (255, 128, 0));
	credits.setActionCommand ("credits");
	credits.addActionListener (this);
	card4.add (winPic);
	card4.add (score);
	card4.add (blank);
	card4.add (blank2);
	card4.add (again);
	card4.add (titleQuit);
	card4.add (credits);
	p_card.add ("4", card4);
    }


    public void creditScreen ()
    {
	card5 = new Panel ();
	JLabel creditsPic = new JLabel (createImageIcon ("credits3.jpg")); //pre-made picture
	JButton back = new JButton ("Back");
	back.setFont (new Font ("Chiller", Font.PLAIN, 45));
	back.setBackground (Color.black);
	back.setForeground (new Color (255, 128, 0));
	back.setActionCommand ("back");
	back.addActionListener (this);
	JButton titleQuit = new JButton ("Quit to Title");
	titleQuit.setFont (new Font ("Chiller", Font.PLAIN, 45));
	titleQuit.setBackground (Color.black);
	titleQuit.setForeground (new Color (255, 128, 0));
	titleQuit.setActionCommand ("titleQuit");
	titleQuit.addActionListener (this);
	JButton again = new JButton ("Play again");
	again.setFont (new Font ("Chiller", Font.PLAIN, 45));
	again.setBackground (Color.black);
	again.setForeground (new Color (255, 128, 0));
	again.setActionCommand ("reset");
	again.addActionListener (this);
	card5.add (creditsPic);
	card5.add (back);
	card5.add (titleQuit);
	card5.add (again);
	p_card.add ("5", card5);
    }


    public void loseScreen (int pts)
    {
	card6 = new Panel ();
	card6.setBackground (Color.black);
	JLabel losePic = new JLabel (createImageIcon ("loseScreen.jpg")); //photoshopped tiger in pre-made picture
	JLabel blank = new JLabel ("                                                                                                                   ");
	blank.setFont (new Font ("OCR A Extended", Font.PLAIN, 30));
	JButton again = new JButton ("Try again");
	again.setFont (new Font ("Chiller", Font.PLAIN, 35));
	again.setBackground (Color.black);
	again.setForeground (new Color (255, 128, 0));
	again.setActionCommand ("reset");
	again.addActionListener (this);
	JButton titleQuit = new JButton ("Quit to Title");
	titleQuit.setFont (new Font ("Chiller", Font.PLAIN, 35));
	titleQuit.setBackground (Color.black);
	titleQuit.setForeground (new Color (255, 128, 0));
	titleQuit.setActionCommand ("titleQuit");
	titleQuit.addActionListener (this);
	card6.add (losePic);
	card6.add (blank);
	card6.add (again);
	card6.add (titleQuit);
	p_card.add ("6", card6);
    }


    public void chooseScreen ()
    {
	card7 = new Panel ();
	card7.setBackground (Color.black);
	Dimension size = new Dimension (700, 65);
	JLabel choose = new JLabel ("Choose your difficulty:");
	choose.setFont (new Font ("OCR A Extended", Font.PLAIN, 50));
	choose.setForeground (Color.white);
	JLabel blank = new JLabel ("                                                                                                                   ");
	blank.setFont (new Font ("OCR A Extended", Font.PLAIN, 20));
	JButton Pie = new JButton ("         Pie         ");
	Pie.setFont (new Font ("OCR A Extended", Font.PLAIN, 45));
	Pie.setBackground (Color.black);
	Pie.setForeground (new Color (255, 128, 0));
	Pie.setActionCommand ("Pie");
	Pie.addActionListener (this);
	Pie.setPreferredSize (size);
	Pie.setToolTipText ("Just in case you are tired of losing.");
	JLabel blank2 = new JLabel ("                                                                                                                   ");
	blank2.setFont (new Font ("OCR A Extended", Font.PLAIN, 45));
	JButton Easy = new JButton ("        Easy        ");
	Easy.setFont (new Font ("OCR A Extended", Font.PLAIN, 45));
	Easy.setBackground (Color.black);
	Easy.setForeground (new Color (255, 128, 0));
	Easy.setActionCommand ("Easy");
	Easy.addActionListener (this);
	Easy.setPreferredSize (size);
	Easy.setToolTipText ("For beginner players.");
	JLabel blank3 = new JLabel ("                                                                                                                   ");
	blank3.setFont (new Font ("OCR A Extended", Font.PLAIN, 45));
	JButton Medium = new JButton ("     Medium     ");
	Medium.setFont (new Font ("OCR A Extended", Font.PLAIN, 45));
	Medium.setBackground (Color.black);
	Medium.setForeground (new Color (255, 128, 0));
	Medium.setActionCommand ("Medium");
	Medium.addActionListener (this);
	Medium.setPreferredSize (size);
	Medium.setToolTipText ("Don't worry, only 20 tigers have escaped.");
	JLabel blank4 = new JLabel ("                                                                                                                   ");
	blank4.setFont (new Font ("OCR A Extended", Font.PLAIN, 45));
	JButton Hard = new JButton ("      Hard      ");
	Hard.setFont (new Font ("OCR A Extended", Font.PLAIN, 45));
	Hard.setBackground (Color.black);
	Hard.setForeground (new Color (255, 128, 0));
	Hard.setActionCommand ("Hard");
	Hard.addActionListener (this);
	Hard.setPreferredSize (size);
	Hard.setToolTipText ("Looking for a challenge? Look no further!");
	JLabel blank5 = new JLabel ("                                                                                                                   ");
	blank5.setFont (new Font ("OCR A Extended", Font.PLAIN, 45));
	JButton Suicide = new JButton ("    Suicide    ");
	Suicide.setFont (new Font ("OCR A Extended", Font.PLAIN, 45));
	Suicide.setBackground (Color.black);
	Suicide.setForeground (new Color (255, 128, 0));
	Suicide.setActionCommand ("Suicide");
	Suicide.addActionListener (this);
	Suicide.setPreferredSize (size);
	Suicide.setToolTipText ("WARNING! Rage Quitting imminent!");
	JLabel blank6 = new JLabel ("                                                                                                                   ");
	blank6.setFont (new Font ("OCR A Extended", Font.PLAIN, 20));
	JButton titleQuit = new JButton ("Quit to Title");
	titleQuit.setFont (new Font ("Chiller", Font.PLAIN, 35));
	titleQuit.setBackground (Color.black);
	titleQuit.setForeground (Color.white);
	titleQuit.setActionCommand ("titleQuit");
	titleQuit.addActionListener (this);
	JButton help = new JButton ("    Help    ");
	help.setFont (new Font ("Chiller", Font.PLAIN, 35));
	help.setBackground (Color.black);
	help.setForeground (Color.white);
	help.setActionCommand ("help");
	help.addActionListener (this);
	JLabel blank7 = new JLabel ("                                                                                                                   ");
	blank7.setFont (new Font ("OCR A Extended", Font.PLAIN, 20));
	card7.add (choose);
	card7.add (blank);
	card7.add (blank6);
	card7.add (Pie);
	card7.add (blank2);
	card7.add (Easy);
	card7.add (blank3);
	card7.add (Medium);
	card7.add (blank4);
	card7.add (Hard);
	card7.add (blank5);
	card7.add (Suicide);
	card7.add (blank7);
	card7.add (titleQuit);
	card7.add (help);
	p_card.add ("7", card7);
    }


    public void actionPerformed (ActionEvent e)
    {
	if (e.getActionCommand ().equals ("quit"))
	    System.exit (0); //exits the applet
	else if (e.getActionCommand ().equals ("play"))
	    cdLayout.show (p_card, "7");
	else if (e.getActionCommand ().equals ("titleQuit"))
	    cdLayout.show (p_card, "1");
	else if (e.getActionCommand ().equals ("help"))
	    cdLayout.show (p_card, "3");
	else if (e.getActionCommand ().equals ("credits"))
	    cdLayout.show (p_card, "5");
	else if (e.getActionCommand ().equals ("back"))
	    cdLayout.show (p_card, "4");
	else if (e.getActionCommand ().equals ("flag")) //starts flag mode
	{
	    pts--;
	    ptsLabel.setText ("Points: " + pts);
	    mode.setText ("   Mode: Flag");
	    n = 2; //defining number for flag mode
	}
	else if (e.getActionCommand ().equals ("default")) //starts default mode
	{
	    pts--;
	    ptsLabel.setText ("Points: " + pts);
	    mode.setText ("   Mode: Default");
	    n = 1; //defining number for default mode
	}
	else if (e.getActionCommand ().equals ("reset"))
	{
	    resetGame (); //calls the reset method
	    cdLayout.show (p_card, "7"); //always shows the games screen
	}
	else if (e.getActionCommand ().equals ("Pie"))
	{
	    resetGame ();
	    pie ();
	    cdLayout.show (p_card, "2");
	    d = 1;
	    numFlags = 5;
	    flagsLeft.setText ("Flags Left: " + numFlags + "    ");
	}
	else if (e.getActionCommand ().equals ("Easy"))
	{
	    resetGame ();
	    easy ();
	    cdLayout.show (p_card, "2");
	    d = 2;
	    numFlags = 10;
	    flagsLeft.setText ("Flags Left: " + numFlags + "    ");
	}
	else if (e.getActionCommand ().equals ("Medium"))
	{
	    resetGame ();
	    medium ();
	    cdLayout.show (p_card, "2");
	    d = 3;
	    numFlags = 20;
	    flagsLeft.setText ("Flags Left: " + numFlags + "    ");
	}
	else if (e.getActionCommand ().equals ("Hard"))
	{
	    resetGame ();
	    hard ();
	    cdLayout.show (p_card, "2");
	    d = 4;
	    numFlags = 30;
	    flagsLeft.setText ("Flags Left: " + numFlags + "    ");
	}
	else if (e.getActionCommand ().equals ("Suicide"))
	{
	    resetGame ();
	    suicide ();
	    cdLayout.show (p_card, "2");
	    d = 4;
	    numFlags = 50;
	    flagsLeft.setText ("Flags Left: " + numFlags + "    ");
	}
	else
	{
	    int pos = Integer.parseInt (e.getActionCommand ()); //gets position
	    pts--;
	    ptsLabel.setText ("Points: " + pts);
	    int x = pos / row;
	    int y = pos % row;
	    if (n == 2) //checks the mode (flag or default)
	    {
		if (numFlags > 0 && flags [x] [y] == 0) //only works if there are more than 0 flags and if there isn't a flag already there
		{
		    flags [x] [y] = 1; //flag
		    numFlags--; //subtracts a flag
		    flagsLeft.setText ("Flags Left: " + numFlags + "    ");
		    if (bombs [x] [y] == 10) //if the user flagged a tiger
			flags [x] [y] = 10;  //10 is stored for that position in the flag tracking array, will come in handy for checkWin
		}
	    }
	    else //if (n==1)
	    {
		if (d == 1) //for pie
		{
		    if (numFlags < 5 && flags [x] [y] == 1) //only works if there are less than 5 flags
		    {
			flags [x] [y] = 0;
			numFlags++; //adds a flag
			flagsLeft.setText ("Flags Left: " + numFlags + "    ");
		    }
		}
		if (d == 2) //for easy
		{
		    if (numFlags < 10 && flags [x] [y] == 1) //only works if there are less than 10 flags
		    {
			flags [x] [y] = 0;
			numFlags++; //adds a flag
			flagsLeft.setText ("Flags Left: " + numFlags + "    ");
		    }
		}
		if (d == 3) //for medium
		{
		    if (numFlags < 20 && flags [x] [y] == 1) //only works if there are less than 20 flags
		    {
			flags [x] [y] = 0;
			numFlags++; //adds a flag
			flagsLeft.setText ("Flags Left: " + numFlags + "    ");
		    }
		}
		if (d == 4) //for hard
		{
		    if (numFlags < 30 && flags [x] [y] == 1) //only works if there are less than 30 flags
		    {
			flags [x] [y] = 0;
			numFlags++; //adds a flag
			flagsLeft.setText ("Flags Left: " + numFlags + "    ");
		    }
		}
		if (d == 5) //for suicide
		{
		    if (numFlags < 50 && flags [x] [y] == 1) //only works if there are less than 30 flags
		    {
			flags [x] [y] = 0;
			numFlags++; //adds a flag
			flagsLeft.setText ("Flags Left: " + numFlags + "    ");
		    }
		}
		else //user clicks on a leaf square (not a flag)
		{
		    flags [x] [y] = 2; //visible
		    around (pos);
		    if (trackNum [x] [y] == 1)
		    {
			draw (pos);
			a [pos].setIcon (createImageIcon (around (pos) + ".jpg")); //only shows the number, does not expand
		    }
		    else
			open (pos);
		    if (bombs [x] [y] == 10) //if the users clicks on a tiger
		    {
			cdLayout.show (p_card, "6"); //shows the lose screen
		    }
		}
	    }
	    draw (pos);
	    if (checkWin ()) //if the user has won by flagging all of the tigers
	    {
		winScreen (pts);
		cdLayout.show (p_card, "4");
	    }
	}
    }


    public void draw (int pos)  //shows the correct picture on the grid based on the values in the tracking array
    {
	int m = 0;
	int x = pos / row;
	int y = pos % row;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < row ; j++)
	    {
		if (flags [i] [j] == 1 || flags [i] [j] == 10)
		    a [m].setIcon (createImageIcon ("flag.jpg"));
		else if (flags [i] [j] == 0) //not visible
		    a [m].setIcon (createImageIcon ("leafTexture.jpg"));
		else if (bombs [i] [j] == 10) //tiger
		    a [m].setIcon (createImageIcon ("stripe.jpg"));
		else
		{
		    if (around (m) > 0)
		    {
			a [m].setIcon (createImageIcon (trackNum [i] [j] + ".jpg")); //handle the number pics
		    }
		    else
		    {
			a [m].setIcon (createImageIcon ("dirt.jpg"));
		    }
		}
		m++;
	    }
	}
    }


    public void open (int pos)  //recursive method to handle the "expansion" after the users clicks on a square
    {
	int x = pos / row;
	int y = pos % row;
	around (pos);
	draw (pos);
	if (x - 1 >= 0 && y - 1 >= 0 && bombs [x - 1] [y - 1] != 10 && flags [x - 1] [y - 1] == 0) //makes sure there are no crashes by excluding any out of bounds array indices
	{
	    flags [x - 1] [y - 1] = 2;
	    if (trackNum [x - 1] [y - 1] == 0) //does not work if there is a number, applies to the rest of the ifs in this method
		open ((x - 1) * row + (y - 1)); //calls itself
	}

	if (x - 1 >= 0 && bombs [x - 1] [y] != 10 && flags [x - 1] [y] == 0)
	{
	    flags [x - 1] [y] = 2;
	    if (trackNum [x - 1] [y] == 0)
		open ((x - 1) * row + (y));
	}
	if (x - 1 >= 0 && y + 1 <= 14 && bombs [x - 1] [y + 1] != 10 && flags [x - 1] [y + 1] == 0)
	{
	    flags [x - 1] [y + 1] = 2;
	    if (trackNum [x - 1] [y + 1] == 0)
		open ((x - 1) * row + (y + 1));
	}
	if (y - 1 > 0 && bombs [x] [y - 1] != 10 && flags [x] [y - 1] == 0)
	{
	    flags [x] [y - 1] = 2;
	    if (trackNum [x] [y - 1] == 0)
		open ((x) * row + (y - 1));
	}
	if (y + 1 <= 14 && bombs [x] [y + 1] != 10 && flags [x] [y + 1] == 0)
	{
	    flags [x] [y + 1] = 2;
	    if (trackNum [x] [y + 1] == 0)
		open ((x) * row + (y + 1));
	}
	if (x + 1 <= 14 && y - 1 >= 0 && bombs [x + 1] [y - 1] != 10 && flags [x + 1] [y - 1] == 0)
	{
	    flags [x + 1] [y - 1] = 2;
	    if (trackNum [x + 1] [y - 1] == 0)
		open ((x + 1) * row + (y - 1));
	}
	if (x + 1 <= 14 && bombs [x + 1] [y] != 10 && flags [x + 1] [y] == 0)
	{
	    flags [x + 1] [y] = 2;
	    if (trackNum [x + 1] [y] == 0)
		open ((x + 1) * row + (y));
	}
	if (x + 1 <= 14 && y + 1 <= 14 && bombs [x + 1] [y + 1] != 10 && flags [x + 1] [y + 1] == 0)
	{
	    flags [x + 1] [y + 1] = 2;
	    if (trackNum [x + 1] [y + 1] == 0)
		open ((x + 1) * row + (y + 1));
	}
	return;
    }


    public int around (int pos)  //checks the 8 squares around whatever square the user clicked on for tigers
    {
	int x = pos / row;
	int y = pos % row;
	int nbrCount = 0;
	if (x - 1 >= 0 && y - 1 >= 0 && bombs [x - 1] [y - 1] == 10) //makes sure there are no crashes by excluding any out of bounds array indices
	    nbrCount++;
	if (x - 1 >= 0 && bombs [x - 1] [y] == 10)
	    nbrCount++;
	if (x - 1 >= 0 && y + 1 <= 14 && bombs [x - 1] [y + 1] == 10)     
	    nbrCount++;
	if (y - 1 >= 0 && bombs [x] [y - 1] == 10)
	    nbrCount++;
	if (y + 1 < row && bombs [x] [y + 1] == 10)
	    nbrCount++;
	if (x + 1 < row && y - 1 >= 0 && bombs [x + 1] [y - 1] == 10)
	    nbrCount++;
	if (x + 1 < row && bombs [x + 1] [y] == 10)
	    nbrCount++;
	if (x + 1 < row && y + 1 < row && bombs [x + 1] [y + 1] == 10)
	    nbrCount++;
	return nbrCount;
    }


    public void resetGame ()  //resets the game screen back to the original one with different tiger positions
    {
	int m = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < row ; j++)
	    {
		flags [i] [j] = 0;
		if (bombs [i] [j] == 10)
		    trackNum [i] [j] = 10;
		else
		    trackNum [i] [j] = around (m);
		bombs [i] [j] = 0;
		a [m].setIcon (createImageIcon ("leafTexture.jpg"));
		m++;
	    }
	}
	for (int i = 0 ; i < a.length ; i++)
	{
	    a [i].setIcon (createImageIcon ("leafTexture.jpg"));
	}
	pts = 999;
	ptsLabel.setText ("Points: " + pts);
	numFlags = 15;
	flagsLeft.setText ("Flags Left: " + numFlags + "    ");
	n = 1;
	mode.setText ("   Mode: Default");
    }


    public boolean checkWin ()  //checks to see if the user has won by flagging all the tigers
    {
	int count = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < row ; j++)
	    {
		if (flags [i] [j] == 10) //if the user flagged the tiger on the [i] [j] position  
		    count++; //the count increases
	    }
	}
	if (d == 1 && count == 5) //depending on the difficulty, the number of flagged tigers to win will change (from 5-50)
	    return true;
	if (d == 2 && count == 10)
	    return true;
	if (d == 3 && count == 20)
	    return true;
	if (d == 4 && count == 30)
	    return true;
	if (d == 5 && count == 50)
	    return true;
	else
	    return false;
    }


    public void pie ()
    {
	for (int i = 0 ; i < 5 ; i++) //random positions for the 5 tigers
	{
	    bombX = (int) ((Math.random () * 15) + 0);
	    bombY = (int) ((Math.random () * 15) + 0);
	    while (bombs [bombX] [bombY] == 10)
	    {
		bombX = (int) ((Math.random () * 15) + 0);
		bombY = (int) ((Math.random () * 15) + 0);
	    }
	    bombs [bombX] [bombY] = 10;
	    numTracking ();
	}
    }


    public void easy ()
    {
	for (int i = 0 ; i < 10 ; i++) //random positions for the 10 tigers
	{
	    bombX = (int) ((Math.random () * 15) + 0);
	    bombY = (int) ((Math.random () * 15) + 0);
	    while (bombs [bombX] [bombY] == 10)
	    {
		bombX = (int) ((Math.random () * 15) + 0);
		bombY = (int) ((Math.random () * 15) + 0);
	    }
	    bombs [bombX] [bombY] = 10;
	    numTracking ();
	}
    }


    public void medium ()
    {
	for (int i = 0 ; i < 20 ; i++) //random positions for the 20 tigers
	{
	    bombX = (int) ((Math.random () * 15) + 0);
	    bombY = (int) ((Math.random () * 15) + 0);
	    while (bombs [bombX] [bombY] == 10)
	    {
		bombX = (int) ((Math.random () * 15) + 0);
		bombY = (int) ((Math.random () * 15) + 0);
	    }
	    bombs [bombX] [bombY] = 10;
	    numTracking ();
	}
    }


    public void hard ()
    {
	for (int i = 0 ; i < 30 ; i++) //random positions for the 30 tigers
	{
	    bombX = (int) ((Math.random () * 15) + 0);
	    bombY = (int) ((Math.random () * 15) + 0);
	    while (bombs [bombX] [bombY] == 10)
	    {
		bombX = (int) ((Math.random () * 15) + 0);
		bombY = (int) ((Math.random () * 15) + 0);
	    }
	    bombs [bombX] [bombY] = 10;
	    numTracking ();
	}
    }


    public void suicide ()
    {
	for (int i = 0 ; i < 50 ; i++) //random positions for the 50 tigers
	{
	    bombX = (int) ((Math.random () * 15) + 0);
	    bombY = (int) ((Math.random () * 15) + 0);
	    while (bombs [bombX] [bombY] == 10)
	    {
		bombX = (int) ((Math.random () * 15) + 0);
		bombY = (int) ((Math.random () * 15) + 0);
	    }
	    bombs [bombX] [bombY] = 10;
	    numTracking ();
	}
    }


    public void numTracking ()
    {
	int m = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < row ; j++)
	    {
		if (bombs [i] [j] == 10) //makes sure that a number (1-8) is not on a bomb
		    trackNum [i] [j] = 10;
		else
		    trackNum [i] [j] = around (m); //if it is not, around method is called to find the neighbor count
		m++;
	    }
	}
    }


    protected static ImageIcon createImageIcon (String path)  //handles all the pictures
    {
	java.net.URL imgURL = tigerFinder.class.getResource (path);
	if (imgURL != null)
	{
	    return new ImageIcon (imgURL);
	}
	else
	{
	    System.err.println ("Couldn't find file: " + path);
	    return null;
	}
    }
}


