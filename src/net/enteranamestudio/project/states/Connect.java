package net.enteranamestudio.project.states;

import java.util.ArrayList;

import net.enteranamestudio.project.Project;
import net.enteranamestudio.project.Resources;
import net.enteranamestudio.project.util.Button;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Connect extends BasicGameState {
	
	private Project project;
	
	private ArrayList<Button> buttons;

	private TextField usernameField;
	private TextField ipField;
	
	private String messageName;
	private String messageIP;
	
	public Connect(Project project) {
		super();
		
		this.project = project;
	}
	
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		Resources.init();
		
		this.buttons = new ArrayList<Button>();
		this.buttons.add(new Button(Project.dimension.width / 2 - 75, 250, 1));
		
		this.usernameField = new TextField(container, Resources.fontMediumSize, Project.dimension.width / 2 - 40, 150, 80, 20);
		this.ipField = new TextField(container, Resources.fontMediumSize, Project.dimension.width / 2 - 40, 215, 80, 20);
		this.ipField.setBorderColor(Color.transparent);
		this.usernameField.setBorderColor(Color.transparent);
		
		this.messageName = "Enter your username";
		this.messageIP = "Enter a valid ip";
	}

	public void render(GameContainer container, StateBasedGame arg1, Graphics g) throws SlickException {
		g.setFont(Resources.font);
		g.setColor(Color.black);
		
		Resources.background.draw();
		
		this.usernameField.render(container, g);
		this.ipField.render(container, g);
		
		for (Button button : buttons) {
			button.render(g);
		}
		
		g.drawString(messageName, Project.dimension.width / 2 - ((messageIP.length() / 4) * 12) - 10, 130);
		g.drawString(messageIP, Project.dimension.width / 2 - ((messageIP.length() / 4) * 12) + 8, 195);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		for (Button button : buttons) {
			if (button.isClicked(input)) {
				switch (button.getID()) {
					case 1:
						if (ipField.getText().equals(""))
							this.messageIP = "Please enter a correct ip";
						
						else if (usernameField.getText().equals(""))
							this.messageName = "Please enter a valid username";
						
						else
							setConnectPolling(usernameField.getText(), ipField.getText(), game);
					break;
				}
			}
		}
		
		if (input.isKeyPressed(Input.KEY_RETURN)) {
			if (ipField.getText().equals(""))
				this.messageIP = "Please enter a correct ip";
			
			else if (usernameField.getText().equals(""))
				this.messageName = "Please enter a valid username";
			
			else
				setConnectPolling(usernameField.getText(), ipField.getText(), game);
		}
	}
	
	public void setConnectPolling(String username, String ip, StateBasedGame game) {
		if (project.connectToServer(username, ip, 20815)) {
			this.messageIP = "Connected!";
			
			game.enterState(10);
		}
		
		else 
			this.messageIP = "Could not connect to server";
	}

	public int getID() {
		return 1;
	}

}
