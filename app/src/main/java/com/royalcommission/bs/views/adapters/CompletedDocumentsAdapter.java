package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.Document;
import com.royalcommission.bs.modules.utils.CommonUtils;

import java.util.List;

/**
 * Created by Prashant on 10/17/2018.
 */
public class CompletedDocumentsAdapter extends RecyclerView.Adapter<CompletedDocumentsAdapter.DocumentHolder> {

    private Context mContext;
    private List<Document> documentList;
    private CompletedDocumentsClickListener documentsClickListener;

    public CompletedDocumentsAdapter(Context context, List<Document> subMenuList, CompletedDocumentsClickListener menuItemClickListener) {
        this.mContext = context;
        this.documentList = subMenuList;
        this.documentsClickListener = menuItemClickListener;
    }

    @NonNull
    @Override
    public CompletedDocumentsAdapter.DocumentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_completed_documents, viewGroup, false);
        return new DocumentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompletedDocumentsAdapter.DocumentHolder subMenuHolder, int position) {
        Document document = documentList.get(position);
        if (document != null) {
            if (CommonUtils.isValidString(document.getFormName()))
                subMenuHolder.docName.setText(document.getFormName());
            subMenuHolder.docImage.setOnClickListener(v -> documentsClickListener.onClick(document));
            subMenuHolder.itemView.setOnClickListener(v -> documentsClickListener.onClick(document));
        }
    }


    @Override
    public int getItemCount() {
        return documentList.size();
    }

    class DocumentHolder extends RecyclerView.ViewHolder {
        private TextView docName;
        private ImageView docImage;

        DocumentHolder(@NonNull View itemView) {
            super(itemView);
            docName = itemView.findViewById(R.id.document);
            docImage = itemView.findViewById(R.id.zoom);
        }
    }

    public interface CompletedDocumentsClickListener {
        void onClick(Document document);
    }
}
