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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TitleScreen extends BasicGameState {
	
	private ArrayList<Button> buttons;

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Resources.init();
		
		this.buttons = new ArrayList<Button>();
		this.buttons.add(new Button(Project.dimension.width / 2 - 75, 240, 0));
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.setFont(Resources.font);
		g.setColor(Color.black);

		Resources.background.draw();
		
		for (Button button : buttons) {
			button.render(g);
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		for (Button button : buttons) {
			if (button.isClicked(input)) {
				switch (button.getID()) {
					case 0:
						game.enterState(1);
					break;
				}
			}
		}
	}

	public int getID() {
		return 0;
	}
	
}
