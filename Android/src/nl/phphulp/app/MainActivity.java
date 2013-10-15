/*
 * Copyright (C) 2013 Koen Vlaswinkel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.phphulp.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {

	// Onze list
	private ListView listView;
	// Onze artikelen
	private ArrayList<Article> articles;
	// Onze adapter
	private ArticleAdapter adapter;

	/**
	 * Deze methode wordt aangeroepen als de activity gecreate wordt, dit
	 * gebeurt ook bij een schermrotatie!
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Zorg voor de goede layout
		setContentView(R.layout.activity_main);

		// We willen een reference naar onze list vanuit onze layout
		this.listView = (ListView) this.findViewById(R.id.listView1);

		// We initialiseren eerst onze lijst van artikelen
		this.articles = new ArrayList<Article>();
		// Dan maken we daarmee onze adapter
		this.adapter = new ArticleAdapter(this, articles);
		// En dan zeggen we tegen de list dat onze adapter hem moet helpen :)
		this.listView.setAdapter(this.adapter);
	}

	/**
	 * Deze methode wordt aangeroepen telkens als het scherm in beeld komt
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// We reloaden de list telkens als het scherm zichtbaar wordt
		this.reloadList();
	}

	/**
	 * Hier verversen we de lijst
	 */
	protected void reloadList() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://www.koenv.nl/phphulp/android/index.php",
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray response) {
						try {
							// Leeg eerst alle articles
							articles.clear();
							for (int i = 0; i < response.length(); i++) {
								// Nu willen we het JSON Object op de plaats i
								JSONObject object = response.getJSONObject(i);
								// Daarin zoeken we alle waarden op nodig voor
								// ons Article object
								int id = object.getInt("id");
								String title = object.getString("title");
								String content = object.getString("content");
								String slug = object.getString("slug");
								// Dan maken we ons Article object
								Article article = new Article(id, title,
										content, slug);
								// En voegen we deze toe aan onze lijst
								articles.add(article);
							}
						} catch (JSONException e) {
							Toast.makeText(MainActivity.this,
									"Er is een onbekende fout opgetreden",
									Toast.LENGTH_LONG).show();
							// Hiermee kan jij de errors zien in de view DDMS
							// (rechtsboven klik je op DDMS in Eclipse)
							Log.e("PHPHulp", response.toString(), e);
						} finally {
							// En hier zeggen we dat er iets is veranderd aan
							// onze lijst en dat deze dus gerefresh moet worden
							adapter.notifyDataSetChanged();
						}
					}

					@Override
					public void onFailure(Throwable e, JSONObject response) {
						try {
							Toast.makeText(
									MainActivity.this,
									"Er is een fout opgetreden: "
											+ response.getString("error"),
									Toast.LENGTH_LONG).show();
							// Hiermee kan jij de errors zien in de view DDMS
							// (rechtsboven klik je op DDMS in Eclipse)
							Log.e("PHPHulp", response.toString(), e);
						} catch (JSONException e1) {
							Toast.makeText(MainActivity.this,
									"Er is een onbekende fout opgetreden",
									Toast.LENGTH_LONG).show();
							// Hiermee kan jij de errors zien in de view DDMS
							// (rechtsboven klik je op DDMS in Eclipse)
							Log.e("PHPHulp", response.toString(), e);
						}
					}

					@Override
					public void onFailure(Throwable e, String response) {
						Toast.makeText(MainActivity.this,
								"Er is een onbekende fout opgetreden",
								Toast.LENGTH_LONG).show();
						// Hiermee kan jij de errors zien in de view DDMS
						// (rechtsboven klik je op DDMS in Eclipse)
						Log.e("PHPHulp", response, e);
					}
				});

	}

}
