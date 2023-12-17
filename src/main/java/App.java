import objects.*;
import rasterize.LineRasterizer;
import rasterize.Raster;
import transforms.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.List;

public class App {
    private JPanel panel;
    private SelectedObject3D selectedObject;
    private final Raster raster;
    private final LineRasterizer lineRasterizer;
    private final VisualizationPipeline pipeline;
    private Scene scene;
    private Timer timer;
    private Point2D mousePosition;
    private double screenRation;
    private boolean isOrthoProjection;
    private final int BLACK = 0x00000000;
    private boolean isEditModeOn = false;
    private FergusonCubic fergusonCubic;
    private BezierCubic bezierCubic;
    private CoonsCubic coonsCubic;

    private Cuboid cuboid;
    private Cube cube;
    private Tetrahedron tetrahedron;
    private Camera camera;
    boolean animateToggle = true;

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

        // init scene objects
        cuboid = new Cuboid();
        cube = new Cube();
        tetrahedron = new Tetrahedron();
        Axes axes = new Axes();
        fergusonCubic = new FergusonCubic();
        bezierCubic = new BezierCubic();
        coonsCubic = new CoonsCubic();

        // add objects to the scene
        scene.addObject3D(cuboid);
        scene.addObject3D(tetrahedron);
        scene.addObject3D(axes);
        scene.addObject3D(cube);
        scene.addObject3D(fergusonCubic);
        scene.addObject3D(bezierCubic);
        scene.addObject3D(coonsCubic);
        isOrthoProjection = false;
    }

    private void initGUI(int width, int height) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + "3. úloha");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT)) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                raster.present(g);
                // draw text onto the screen
                g.setColor(new Color(0xFFADD8E6));
                raster.drawString(
                        "Camera x position: " + Math.round(camera.getPosition().getX()),
                        (double) raster.getWidth() -150,
                        ((double) raster.getHeight() -90), g);
                raster.drawString(
                        "Camera y position: " + Math.round(camera.getPosition().getY()),
                        (double) raster.getWidth() -150,
                        ((double) raster.getHeight() -70), g);
                raster.drawString(
                        "Camera z position: " + Math.round(camera.getPosition().getZ()),
                        (double) raster.getWidth() -150,
                        ((double) raster.getHeight() -50), g);
                raster.drawString(
                        "Azimuth: " + Math.round(Math.toDegrees(camera.getAzimuth())),
                        (double) raster.getWidth() -150,
                        ((double) raster.getHeight() -120), g);
                raster.drawString(
                        "Zenith: " + Math.round(Math.toDegrees(camera.getZenith())),
                        (double) raster.getWidth() -150,
                        ((double) raster.getHeight() -140), g);
            }
        };
        frame.add(panel, BorderLayout.CENTER);

        // Create selected object
        selectedObject = new SelectedObject3D();
        // Create buttons
        JButton orthoProjectionBtn = new JButton("TURN ORTHOGONAL projection ON");
        JButton editModeBtn = new JButton("TURN EDIT mode ON");
        JButton selectCubeBtn = new JButton("Select cube");
        JButton selectCuboidBtn = new JButton("Select cuboid");
        JButton selectTetrahedronBtn = new JButton("Select tetrahedron");
        JButton selectFergusonBtn = new JButton("Select ferguson");
        JButton selectBezierBtn = new JButton("Select bezier");
        JButton selectCoonsBtn = new JButton("Select coons");
        JButton moveInDirectionXBtn = new JButton("Move in direction X");
        JButton moveInDirectionYBtn = new JButton("Move in direction Y");
        JButton moveInDirectionZBtn = new JButton("Move in direction Z");
        JButton rotateAroundAxisXBtn = new JButton("Rotate around axis X");
        JButton rotateAroundAxisYBtn = new JButton("Rotate around axis Y");
        JButton rotateAroundAxisZBtn = new JButton("Rotate around axis Z");
        JButton makeBiggerBtn = new JButton("Make bigger (scale)");
        JButton makeSmallerBtn = new JButton("Make smaller (scale)");

        // Add buttons to the panel
        panel.add(orthoProjectionBtn);
        panel.add(editModeBtn);

        // Attach ActionListeners to buttons
        orthoProjectionBtn.addActionListener(e -> {
            if (!isOrthoProjection) {
                isOrthoProjection = true;
                orthoProjectionBtn.setText("TURN PERSPECTIVE projection ON");
                panel.requestFocusInWindow();
                updateCanvas();
            } else {
                isOrthoProjection = false;
                orthoProjectionBtn.setText("TURN ORTHOGONAL projection ON");
                panel.requestFocusInWindow();
                updateCanvas();
            }
        });
        editModeBtn.addActionListener(e -> {
            if (!isEditModeOn) {
                isEditModeOn = true;
                editModeBtn.setText("TURN EDIT mode OFF");

                // Add buttons to the panel
                panel.add(selectCubeBtn);
                panel.add(selectCuboidBtn);
                panel.add(selectTetrahedronBtn);
                panel.add(selectFergusonBtn);
                panel.add(selectBezierBtn);
                panel.add(selectCoonsBtn);
                panel.add(moveInDirectionXBtn);
                panel.add(moveInDirectionYBtn);
                panel.add(moveInDirectionZBtn);
                panel.add(rotateAroundAxisXBtn);
                panel.add(rotateAroundAxisYBtn);
                panel.add(rotateAroundAxisZBtn);
                panel.add(makeBiggerBtn);
                panel.add(makeSmallerBtn);
                updateCanvas();
            } else {
                isEditModeOn = false;
                editModeBtn.setText("TURN EDIT mode ON");

                // Add buttons to the panel
                panel.remove(selectCubeBtn);
                panel.remove(selectCuboidBtn);
                panel.remove(selectTetrahedronBtn);
                panel.remove(selectFergusonBtn);
                panel.remove(selectBezierBtn);
                panel.remove(selectCoonsBtn);
                panel.remove(moveInDirectionXBtn);
                panel.remove(moveInDirectionYBtn);
                panel.remove(moveInDirectionZBtn);
                panel.remove(rotateAroundAxisXBtn);
                panel.remove(rotateAroundAxisYBtn);
                panel.remove(rotateAroundAxisZBtn);
                panel.remove(makeBiggerBtn);
                panel.remove(makeSmallerBtn);
                panel.requestFocusInWindow();
                updateCanvas();
            }
        });
        selectCubeBtn.addActionListener(e -> {selectedObject.setSelectedObject(cube);panel.requestFocusInWindow();});

        selectCuboidBtn.addActionListener(e -> {selectedObject.setSelectedObject(cuboid);panel.requestFocusInWindow();}
        );
        selectTetrahedronBtn.addActionListener(e -> {selectedObject.setSelectedObject(tetrahedron);panel.requestFocusInWindow();}
        );
        selectFergusonBtn.addActionListener(e -> {selectedObject.setSelectedObject(fergusonCubic);panel.requestFocusInWindow();}
        );
        selectBezierBtn.addActionListener(e -> {selectedObject.setSelectedObject(bezierCubic);panel.requestFocusInWindow();}
        );
        selectCoonsBtn.addActionListener(e -> {selectedObject.setSelectedObject(coonsCubic);panel.requestFocusInWindow();}
        );
        moveInDirectionXBtn.addActionListener(e -> {
            selectedObject.getObject3D().moveInDirectionX();
            updateCanvas();
        });
        moveInDirectionYBtn.addActionListener(e -> {
            selectedObject.getObject3D().moveInDirectionY();
            updateCanvas();
        });
        moveInDirectionZBtn.addActionListener(e -> {
            selectedObject.getObject3D().moveInDirectionZ();
            updateCanvas();
        });
        rotateAroundAxisXBtn.addActionListener(e -> {
            selectedObject.getObject3D().rotateAroundAxisX();
            updateCanvas();
        });
        rotateAroundAxisYBtn.addActionListener(e -> {
            selectedObject.getObject3D().rotateAroundAxisY();
            updateCanvas();
        });
        rotateAroundAxisZBtn.addActionListener(e -> {
            selectedObject.getObject3D().rotateAroundAxisZ();
            updateCanvas();
        });
        makeBiggerBtn.addActionListener(e -> {
            selectedObject.getObject3D().makeBigger();
            updateCanvas();
        });
        makeSmallerBtn.addActionListener(e -> {
            selectedObject.getObject3D().makeSmaller();
            updateCanvas();
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

    /**
     * For initializing all listeners except action listeners.
     * @param panel component that contains buttons, text fields, etc. that generate events when interacted with.
     */
    private void initListeners(JPanel panel) {
            /*
             * For changing the azimuth and zenith of the Camera object
             */
            panel.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (!isEditModeOn) {
                        if (e.getY() < mousePosition.getY()) {
                            camera = camera.addZenith(Math.PI / (360));
                        }
                        if (e.getY() > mousePosition.getY()) {
                            camera = camera.addZenith(-Math.PI / (360));
                        }
                        if (e.getX() < mousePosition.getX()) {
                            camera = camera.addAzimuth(Math.PI / (360));
                        }
                        if (e.getX() > mousePosition.getX()) {
                            camera = camera.addAzimuth(-Math.PI / (360));
                        }
                        mousePosition = mousePosition.withX(e.getX());
                        mousePosition = mousePosition.withY(e.getY());
                        updateCanvas();
                    }
                }
            });
        /*
         * For changing the direction and speed of the Camera object
         */
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
                            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                                animateToggle = false;
                            }
                            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                                animateToggle = true;
                                startTimer();
                            }
                            updateCanvas();
                    }
            }) ;
    }

    /**
     * For initializing the Camera with default values.
     */
    private void initCamera() {
        camera = new Camera().
                withAzimuth(Math.PI/4).
                withZenith(-Math.PI/2).
                withPosition(new Vec3D(2,0,20));
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
        updateScene();
        panel.repaint();

    }
    private void startTimer() {
        timer = new Timer(10, e -> {
            updateCanvas();
            if (animateToggle) {
                    selectedObject.getObject3D().rotateAroundAxisX();
                    updateCanvas();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App(1000, 800).startTimer());
    }

}