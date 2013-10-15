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

	static class ViewHolder {
		public TextView title;
		public TextView text;
	}

	public ArticleAdapter(Context context, ArrayList<Article> items) {
		super();
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			view = inflater.inflate(android.R.layout.simple_list_item_2,
					parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = (TextView) view.findViewById(android.R.id.text1);
			viewHolder.text = (TextView) view.findViewById(android.R.id.text2);
			view.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) view.getTag();

		final Article article = items.get(position);

		holder.title.setText(article.getTitle());
		holder.text.setText(article.getContent());

		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(
						context,
						"You clicked on article " + article.getId()
								+ " with slug " + article.getSlug(),
						Toast.LENGTH_LONG).show();

			}
		});

		return view;
	}

}
