import objects.*;
import objects.Polygon;
import rasterize.LineRasterizer;
import rasterize.PolygonRasterizer;
import rasterize.Raster;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class App {

    private JPanel panel;
    private final Raster raster;
    private final LineRasterizer lineRasterizer;
    private final VisualizationPipeline pipeline;
    private Scene scene;
    private Point2D mousePosition;
    private double screenRation;
    private boolean isOrthoProjection;
    private final int BLACK = 0x00000000;
    Camera camera;

    public App(int width, int height) {
        raster = new Raster(width, height);
        lineRasterizer = new LineRasterizer();
        pipeline = new VisualizationPipeline(raster, lineRasterizer);
        initCamera();
        initScene();
        initGUI(width, height);
        updateCanvas();
    }
    private void initScene() {
        screenRation = (double)raster.getHeight()/raster.getWidth();
        scene = new Scene();

        Cuboid cuboid = new Cuboid();
        Cube cube = new Cube();
        Tetrahedron tetrahedron = new Tetrahedron();
        Axes axes = new Axes();

        scene.addObject3D(cuboid);
        scene.addObject3D(tetrahedron);
        scene.addObject3D(axes);
        scene.addObject3D(cube);

        isOrthoProjection = false;
    }

    private void initGUI(int width, int height) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + "3. úloha");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                raster.present(g);
            }
        };
        frame.add(panel, BorderLayout.CENTER);
        // Create buttons
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
// Add buttons to the panel
        panel.add(button1);
        panel.add(button2);
        // Attach ActionListener to buttons
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button1 click
                JOptionPane.showMessageDialog(frame, "Button 1 clicked!");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button2 click
                JOptionPane.showMessageDialog(frame, "Button 2 clicked!");
            }
        });
        // Add the panel to the frame
        frame.getContentPane().add(panel);

        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        initListeners(panel);
    }
    private void initListeners(JPanel panel) {
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(e.getY()<mousePosition.getY()){
                    camera = camera.addZenith(Math.PI/(360));
                }
                if(e.getY()>mousePosition.getY()){
                    camera = camera.addZenith(-Math.PI/(360));
                }
                if(e.getX()<mousePosition.getX()){
                    camera = camera.addAzimuth(Math.PI/(360));
                }
                if(e.getX()>mousePosition.getX()){
                    camera = camera.addAzimuth(-Math.PI/(360));
                }
                mousePosition = mousePosition.withX(e.getX());
                mousePosition = mousePosition.withY(e.getY());
                updateCanvas();
            }
        });
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                double speed = 0.3;
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    camera = camera.forward(speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    camera = camera.left(speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    camera = camera.backward(speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    camera = camera.right(speed);
                }
                updateCanvas();
            }
        }) ;
    }
    private void initCamera() {
        camera = new Camera().
                withAzimuth(Math.PI/4).
                withZenith(-Math.PI/2).
                withPosition(new Vec3D(2,0,13));
        mousePosition = new Point2D((double) raster.getWidth() / 2, (double) raster.getHeight() / 2);
    }

    /**
     * Function for updating the scene and rendering it on the raster. NOTE: Doesn't clear previous drawings. Updates view matrix from camera, projection matrix and renders every solid.
     */
    private void updateScene() {
        Mat4 view = new Mat4(camera.getViewMatrix());
        pipeline.setView(view);
        pipeline.setProj(isOrthoProjection ? new Mat4OrthoRH(20, 20, 0.1,200) : new Mat4PerspRH((Math.PI/4), screenRation, 0.1, 200));
        pipeline.render(scene);
    }

    /**
     * Clears all drawings and draws new ones.
     */
    private void updateCanvas() {
        raster.clear(BLACK);
        //renderAxis();
        updateScene();
        panel.repaint();

    }
    private void start() {
        updateCanvas();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App(800, 600).start());
    }

}