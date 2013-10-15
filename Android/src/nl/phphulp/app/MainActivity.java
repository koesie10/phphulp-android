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
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {

	private ListView listView;
	private ArrayList<Article> articles;
	private ArticleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.listView = (ListView) this.findViewById(R.id.listView1);

		this.articles = new ArrayList<Article>();
		this.adapter = new ArticleAdapter(this, articles);
		this.listView.setAdapter(this.adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.reloadList();
	}

	protected void reloadList() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://www.koenv.nl/phphulp/android/index.php",
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray response) {
						try {
							articles.clear();
							for (int i = 0; i < response.length(); i++) {
								JSONObject object = response.getJSONObject(i);
								int id = object.getInt("id");
								String title = object.getString("title");
								String content = object.getString("content");
								String slug = object.getString("slug");
								Article article = new Article(id, title, content, slug);
								articles.add(article);
							}
							adapter.notifyDataSetChanged();
						} catch (JSONException e) {
							Toast.makeText(MainActivity.this,
									"Er is een onbekende fout opgetreden",
									Toast.LENGTH_LONG).show();
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
						} catch (JSONException e1) {
							Toast.makeText(MainActivity.this,
									"Er is een onbekende fout opgetreden",
									Toast.LENGTH_LONG).show();
						}
					}
				});

	}

}
