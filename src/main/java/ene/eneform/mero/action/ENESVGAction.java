/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ene.eneform.mero.action;

//import ene.racingcolours.shapes.svg.ENEShapeSVG;

import ene.eneform.mero.colours.ENEColoursElementPattern;
import ene.eneform.mero.config.ENEColoursEnvironment;
import ene.eneform.mero.utils.ENEFillItem;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.io.Serializable;

/**
 *
 * @author Simon
 */
    public class ENESVGAction extends ENEPatternAction implements Serializable
    {
        protected String m_strSVGName;
        protected Rectangle[] m_rectangles = new Rectangle[2];
        
        protected Point[][] m_points = {{new Point(0, 0)},   {new Point(0, 0)}};
        protected Dimension[] m_dimensions = {new Dimension(0, 0), new Dimension(0, 0)};

        protected boolean m_bHasRotation;
        protected int[] m_rotate_degrees = null;
       
        protected int m_nRectangles;
         
        public static int STANDARD_TEMPLATE_TYPE = 0;
        public static int MERO_TEMPLATE_TYPE = 1;
        
        public ENESVGAction(String strSVGName)
        {
            m_strSVGName = strSVGName;
        }
        public ENESVGAction(String strSVGName, Point[][] points, Dimension[] dimensions, int[] rotateAngles)
        {
            m_strSVGName = strSVGName;
            m_points = points;
            m_dimensions = dimensions;
            m_nRectangles = points[STANDARD_TEMPLATE_TYPE].length;
            m_rotate_degrees = rotateAngles;
            m_bHasRotation = true;
        }
       public ENESVGAction(String strSVGName, Point[][] points, Dimension[] dimensions)
        {
            m_strSVGName = strSVGName;
            m_points = points;
            m_dimensions = dimensions;
            m_nRectangles = points[STANDARD_TEMPLATE_TYPE].length;
            m_bHasRotation = false;
        }
        public void setDimensions(int nTemplateType, Rectangle rectangle)
        {
            // The x, y co-ords of the rectangle are relative to the default 
            m_rectangles[nTemplateType] = rectangle;    // assumes that item onlyappears once i.e.  Not "Five" or similar
        }
        
       public String getSVGName()
       {
           return m_strSVGName;
       }
       public Rectangle[] getMeroRectangles()
       {
           return getDisplayRectangles(1);
       }
       public Rectangle[] getAWTRectangles()
       {
           return getDisplayRectangles(0);
       }
       public boolean hasRotation()
       {
           return m_bHasRotation;
       }
       public int getRotateDegrees(int nItem)
       {
           return m_rotate_degrees[nItem];
       }
       private Rectangle[] getDisplayRectangles(int nTemplateType)
        {
            // depends on m_element
            Point[] aDefaultPoints = m_points[nTemplateType];
            Rectangle[] aDisplayRectangles = new Rectangle[aDefaultPoints.length];
            Dimension dimension = m_dimensions[nTemplateType];
            
            Rectangle displayRectangle = m_rectangles[nTemplateType]; 
            if (displayRectangle != null)
            {
                // this will be the case if SetDimensions has been called
                // either by attribute mero or by attribute dimensions (AWT) in patterns.xml
                for(int i = 0; i < aDefaultPoints.length; i++)
                {
                    aDisplayRectangles[i] = new Rectangle((int)(aDefaultPoints[i].getX() + displayRectangle.getX()), (int)(aDefaultPoints[i].getY() + displayRectangle.getY()), (int) (dimension.getWidth() + displayRectangle.getWidth()), (int) (dimension.getHeight() + displayRectangle.getHeight()));
                }
            }
            else
            {
                for(int i = 0; i < aDefaultPoints.length; i++)
                {
                    aDisplayRectangles[i] = new Rectangle((int)aDefaultPoints[i].getX(), (int)aDefaultPoints[i].getY(), (int) dimension.getWidth(), (int) dimension.getHeight());
                }
            }

            return aDisplayRectangles;
        }
@Override public void drawPattern(Graphics g, ENEFillItem colour, ENEColoursElementPattern pattern, Color pageColour)
{
    // do nothing for now
    SVGDocument doc = ENEColoursEnvironment.getInstance().getSVGDocument(m_strSVGName);
    convertColour(doc, "colour0", colour.getText());
    int nColours = pattern.getColourCount();
    for(int i = 1; i <= nColours; i++)
    {
        convertColour(doc, "colour" + i, pattern.getColour(i).getText());
    }
    
    Rectangle[] aRectangles = getAWTRectangles();
    // TO DO: rotating about centre of rectangle - ideally should be centre of resized image
    GraphicsNode gn = ENEColoursEnvironment.getInstance().createGraphicsNode(doc);
    for(int i = 0; i < aRectangles.length; i++)
    {
       fillShape((Graphics2D)g, gn, aRectangles[i]);
    }
 }
private void convertColour(Document doc, String strId, String strColour)
{
    Element colour = doc.getElementById(strId);
    if (colour != null)
    {
        ((Element)colour.getElementsByTagName("stop").item(0)).setAttribute("stop-color", strColour);
    }
}
 private void fillShape(Graphics2D g, GraphicsNode gn, Rectangle rectangle)
{
   g.drawRect((int)rectangle.getX(), (int)rectangle.getY(), (int)rectangle.getWidth(), (int)rectangle.getHeight());
   g.translate(rectangle.getX(), rectangle.getY());

   AffineTransform transform = ENEColoursEnvironment.getInstance().transformGraphicsNode(gn.getBounds(), rectangle);
   
   gn.setTransform(transform);

   gn.paint((Graphics2D)g);

   try
   {
    gn.setTransform(transform.createInverse());
   }
   catch(NoninvertibleTransformException e)
   {
       System.out.println("NoninvertibleTransformException: " + e.getMessage());
   }
   g.translate(-rectangle.getX(), -rectangle.getY());
}
    }

