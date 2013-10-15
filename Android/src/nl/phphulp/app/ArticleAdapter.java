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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Article> items;
	
	/**
	 * Dit wordt gebruikt voor een hogere snelheid
	 */
	static class ViewHolder {
		public TextView title;
		public TextView text;
	}

	public ArticleAdapter(Context context, ArrayList<Article> items) {
		super();
		this.context = context;
		this.items = items;
	}

	/**
	 * Hoe groot is onze lijst?
	 * 
	 * @return grootte van de lijst
	 */
	@Override
	public int getCount() {
		return items.size();
	}

	/**
	 * Een item uit de lijst
	 * 
	 * @return een object dat het item is
	 */
	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	/**
	 * Dit is een beetje onzinnig voor onze applicatie
	 * 
	 * @return de positie
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Hier maken we de view die zichtbaar is in de lijst
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * 
	 * @return De view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// Zorg ervoor dat we een layout kunnen maken
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// Zorg voor een standaard view met 2 regels
			view = inflater.inflate(android.R.layout.simple_list_item_2,
					parent, false);

			// We gaan voor wat snelheid zorgen
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = (TextView) view.findViewById(android.R.id.text1);
			viewHolder.text = (TextView) view.findViewById(android.R.id.text2);
			// En deze snelheid opslaan
			view.setTag(viewHolder);
		}

		// Hier kunnen we de snelheid weer terugkrijgen
		ViewHolder holder = (ViewHolder) view.getTag();

		// Dit is ons artikel dat we moeten laten zien
		final Article article = items.get(position);

		// We gaan op de eerste regel een titel laten zien
		holder.title.setText(article.getTitle());
		// En op de tweede regel wat content
		holder.text.setText(article.getContent());

		// En nu gaan we zorgen dat er een 'Toast' verschijnt als je op het item
		// klikt
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Hier maken we een 'Toast'
				Toast.makeText(
						context,
						"Je hebt op artikel " + article.getId()
								+ " geklikt met slug " + article.getSlug(),
						Toast.LENGTH_LONG).show();

			}
		});

		return view;
	}

}
