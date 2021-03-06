package edu.uta.byos.Managers;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import edu.uta.byos.MainMenuScene;
import edu.uta.byos.ManagedScene;
import edu.uta.byos.SplashScene;
import edu.uta.byos.TableauScene;
/**
* ********** [ ByOS ] ***********
* @Description A solitaire game
* @Class    | SceneManager
*           | Responsible for switching between scenes and keeping track of the current
*           | displayed scene.
*           | Design Pattern: SINGLETON
*           | Enum: Shows the scene type
* @authors ruby_
* @version 1.0
* ***************************************
*/

public class SceneManager {

    // -------------------------------
    // Constants
    // -------------------------------
    private static final SceneManager INSTANCE = new SceneManager();

    // -------------------------------
    // Constructors
    // -------------------------------
    private SceneManager() {
    }

    // -------------------------------
    // Scenes
    // -------------------------------
    private ManagedScene splashScene;
    private ManagedScene menuScene;
    private ManagedScene gameScene;
    private ManagedScene loadingScene;

    private ManagedScene currentScene;

    // -------------------------------
    // Fields
    // -------------------------------
    public enum SceneType {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
    }

    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    private Engine mEngine = ResourceManager.getInstance().engine;

    // -------------------------------
    // Getter & Setter
    // -------------------------------
    /* Singleton reference to the global SceneManager */
    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public SceneType getCurrentSceneType() {
        return currentSceneType;
    }

    public ManagedScene getCurrentScene() {
        return currentScene;
    }

    // -------------------------------
    // public Methods
    // -------------------------------
    /* Set scene */
    public void setScene(ManagedScene pScene) {
        mEngine.setScene(pScene);
        currentScene = pScene;
        currentSceneType = pScene.getSceneType();
    }

    /* Switch scene */
    public void setScene(SceneType pSceneType) {
        switch (pSceneType)
        {
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            default:
                break;
        }
    }

    // ============================ INIT SCENE (SPLASH) ================= //
    /* Initialize the splash scene */
    public void onShowSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        ResourceManager.loadSplashResources();
        splashScene = new SplashScene();
        splashScene.onShowScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    /* Dispose splash scene -- after load the menu */
    private void onDisposeSplashScene() {
        ResourceManager.unloadSplashResources();
        splashScene.onDisposeScene();
        splashScene = null;
    }

    // ============================ SCENE (MENU) ================= //
    /* Initialize the main menu */
    public void creatMenuScene() {
        ResourceManager.loadMenuResources();
        menuScene = new MainMenuScene();
        menuScene.onShowScene();
        setScene(menuScene);
//        disposeSplashScene();
    }

    // ============================ SCENE (GAME) ================= //
    /* Initialize the game scene */
    public void onShowGameScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        ResourceManager.loadGameResources();
        gameScene = new TableauScene();
        gameScene.onShowScene();
        currentScene = gameScene;
        pOnCreateSceneCallback.onCreateSceneFinished(gameScene);
    }

}
