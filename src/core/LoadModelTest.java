package core;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class LoadModelTest<P extends ModelLoader.ModelParameters> extends Game {

	public PerspectiveCamera cam;
	public Model model;
	public Model model2;
	public ModelInstance instance;
	public ModelInstance instance2;
	public ModelBatch modelBatch;
	public Environment environment;
	public CameraInputController camController;
	public SpriteBatch spriteBatch;
	public DecalBatch decalBatch;
	public Texture options;

	public AssetManager assets;
	public ArrayList<ModelInstance> instances = new ArrayList<ModelInstance>();
	public boolean loading;

	public ArrayList<ModelInstance> blocks = new ArrayList<ModelInstance>();
	public ArrayList<ModelInstance> invaders = new ArrayList<ModelInstance>();
	public ModelInstance pok1;
	public ModelInstance pok2;

	public ArrayList<Decal> decals = new ArrayList<Decal>();

	private Sprite bg;

	@Override
	public void create() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1000f, -800f, -200f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(30f, 20f, 50f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		modelBatch = new ModelBatch();
		ObjLoader loader = new ObjLoader();

		String pok = "Blastoise";

		String pok2 = "Slowbro";

		model = loader.loadModel(Gdx.files.internal("res/Models/" + pok + "/" + pok + ".obj"), true);
		instance = new ModelInstance(model);

		model2 = loader.loadModel(Gdx.files.internal("res/Models/" + pok2 + "/" + pok2 + ".obj"), true);
		instance2 = new ModelInstance(model2);

		assets = new AssetManager();
		assets.load("res/Models/Slowbro/Slowbro.obj", Model.class);
		assets.load("res/Models/Blastoise/Blastoise.obj", Model.class);
		assets.load("res/Models/Aerodactyl/Aerodactyl.obj", Model.class);

		bg = new Sprite(new Texture("res/imgs/batallas/battlebgForestEve.png"));

		loading = true;

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		spriteBatch = new SpriteBatch();

		// Gdx.input.setInputProcessor(controller);

		decalBatch = new DecalBatch(new CameraGroupStrategy(cam));

		TextureRegion[] textures = {
				new TextureRegion(new Texture(Gdx.files.internal("res/imgs/batallas/playerbaseForestGrassEve.png"))),
				new TextureRegion(new Texture("res/imgs/batallas/enemybaseFieldGrassEve.png")) };

		Decal decal = Decal.newDecal(1, 1, textures[1]);
		decal.setPosition(0, 0, 35);
		decal.setDimensions(35, 35);
		decals.add(decal);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	private void doneLoading() {
		pok1 = new ModelInstance(assets.get("res/Models/Slowbro/Slowbro.obj", Model.class));
		Matrix4 tr = new Matrix4();
		tr.setToTranslation(0, 0, 35);
		Matrix4 rt = new Matrix4();
		rt.setToRotation(Vector3.X, 90);
		Matrix4 rt2 = new Matrix4();
		rt2.setToRotation(Vector3.Z, 180);
		tr = tr.mul(rt);
		tr = tr.mul(rt2);
		pok1.transform = tr;

		instances.add(pok1);

		Model pok2Model = assets.get("res/Models/Aerodactyl/Aerodactyl.obj", Model.class);

		ModelInstance block = new ModelInstance(pok2Model);
		block.transform.setToTranslation(0, 0, -35);
		instances.add(block);

		loading = false;
	}

	@Override
	public void render() {

		if (loading && assets.update())
			doneLoading();
		
		camController.update();
		
		


		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		spriteBatch.begin();
		bg.draw(spriteBatch);
		bg.setSize(720, 540);
		spriteBatch.end();

		modelBatch.begin(cam);
		modelBatch.render(instances, environment);
		modelBatch.end();
		
		for (int i = 0; i < decals.size(); i++) {
			Decal decal = decals.get(i);
			// billboarding for ortho cam :)
			// dir.set(-camera.direction.x, -camera.direction.y,
			// -camera.direction.z);
			// decal.setRotation(dir, Vector3.Y);

			// billboarding for perspective cam
			//decal.lookAt(cam.position, cam.up);

			decalBatch.add(decal);
		}
		decalBatch.flush();



		// camController.update();
		//
		// Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight());
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		//
		// modelBatch.begin(cam);
		// modelBatch.render(instance, environment);
		//
		// modelBatch.render(instance2, environment);
		// modelBatch.end();

		// spriteBatch.begin();
		// spriteBatch.draw(options, 100, 100);
		// spriteBatch.end();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		model.dispose();
	}

}
