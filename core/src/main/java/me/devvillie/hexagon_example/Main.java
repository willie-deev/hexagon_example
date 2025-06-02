package me.devvillie.hexagon_example;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main implements ApplicationListener{
	TiledMap tileMap;
	TiledMapTileLayer tiledMapTileLayer;
	FixedHexagonalTiledMapRenderer fixedHexagonalTiledMapRenderer;
	HexagonalTiledMapRenderer originalHexagonalTiledMapRenderer;
	Texture texture;
	TextureRegion textureRegion;
	AssetManager assetManager;
	OrthographicCamera camera;
	Viewport viewport;
	boolean useFixed = true;

	@Override
	public void create(){
		// Prepare your application here.
		assetManager = new AssetManager();
		assetManager.load("test.png", Texture.class);
		assetManager.finishLoading();
		texture = assetManager.get("test.png");
		textureRegion = new TextureRegion(texture);
		tileMap = new TiledMap();
		tiledMapTileLayer = new TiledMapTileLayer(16, 16, 32, 32);
		TiledMapTile tiledMapTile = new StaticTiledMapTile(textureRegion);
		TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell().setTile(tiledMapTile);
		tiledMapTileLayer.setOffsetX(64);
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 16; j++){
				tiledMapTileLayer.setCell(i, j, cell);
			}
		}
		tileMap.getLayers().add(tiledMapTileLayer);
		if(useFixed){
			fixedHexagonalTiledMapRenderer = new FixedHexagonalTiledMapRenderer(tileMap);
		}else{
			originalHexagonalTiledMapRenderer = new HexagonalTiledMapRenderer(tileMap);
		}
		camera = new OrthographicCamera(640, 480);
		viewport = new ExtendViewport(640, 480, camera);
		camera.zoom = 1f;
	}

	@Override
	public void resize(int width, int height){
		// Resize your application here. The parameters represent the new window size.
		viewport.update(width, height);
		camera.update();
	}

	@Override
	public void render(){
		// Draw your application here.
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			camera.translate(0, 1, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			camera.translate(-1, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			camera.translate(0, -1, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			camera.translate(1, 0, 0);
		}
		ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
		viewport.apply();
		camera.update();
		if(useFixed){
			fixedHexagonalTiledMapRenderer.setView(camera);
			fixedHexagonalTiledMapRenderer.render();
		}else{
			originalHexagonalTiledMapRenderer.setView(camera);
			originalHexagonalTiledMapRenderer.render();
		}
	}

	@Override
	public void pause(){
		// Invoked when your application is paused.
	}

	@Override
	public void resume(){
		// Invoked when your application is resumed after pause.
	}

	@Override
	public void dispose(){
		// Destroy application's resources here.
	}
}