/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import net.thoughtmerge.jevolution.components.RenderableBox;
import net.thoughtmerge.jevolution.components.Transform2D;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author evan.gates
 */
public class BoxRendering extends EntityProcessingSystem {
  @Mapper ComponentMapper<Transform2D> transformMapper;
  @Mapper ComponentMapper<RenderableBox> renderableMapper;

  @SuppressWarnings("unchecked")
  public BoxRendering() {
    super(Aspect.getAspectForAll(Transform2D.class, RenderableBox.class));
  }

  @Override
  protected void process(Entity entity) {
    Transform2D tx = transformMapper.get(entity);
    RenderableBox renderable = renderableMapper.get(entity);

    // set the color of the quad (R, G, B, A)
    GL11.glColor4f(renderable.red, renderable.green, renderable.blue, renderable.alpha);

    // draw the quad
    GL11.glPushMatrix();
      GL11.glTranslatef(tx.x, tx.y, 0);
      GL11.glRotatef(tx.rotationInDegrees, 0f, 0f, 1f);
      GL11.glTranslatef(-tx.x, -tx.y, 0);

      GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(tx.x - renderable.width/2.0f, tx.y - renderable.height/2.0f);
        GL11.glVertex2f(tx.x + renderable.width/2.0f, tx.y - renderable.height/2.0f);
        GL11.glVertex2f(tx.x + renderable.width/2.0f, tx.y + renderable.height/2.0f);
        GL11.glVertex2f(tx.x - renderable.width/2.0f, tx.y + renderable.height/2.0f);
      GL11.glEnd();
    GL11.glPopMatrix();

  }

}
