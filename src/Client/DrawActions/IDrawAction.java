package Client.DrawActions;

import java.awt.*;
import java.io.Serializable;

public interface IDrawAction extends Serializable {
    void draw(Graphics2D graphics);
}
