package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.RequestedDocumentStatus;
import com.royalcommission.bs.modules.utils.CommonUtils;

import java.util.List;

/**
 * Created by Prashant on 10/17/2018.
 */
public class DocumentsStatusAdapter extends RecyclerView.Adapter<DocumentsStatusAdapter.DocumentHolder> {

    private Context mContext;
    private List<RequestedDocumentStatus> documentStatuses;
    private DocumentsStatusClickListener statusClickListener;

    public DocumentsStatusAdapter(Context context, List<RequestedDocumentStatus> subMenuList, DocumentsStatusClickListener menuItemClickListener) {
        this.mContext = context;
        this.documentStatuses = subMenuList;
        this.statusClickListener = menuItemClickListener;
    }

    @NonNull
    @Override
    public DocumentsStatusAdapter.DocumentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_documents_status, viewGroup, false);
        return new DocumentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DocumentsStatusAdapter.DocumentHolder subMenuHolder, int position) {
        RequestedDocumentStatus requestedDocumentStatus = documentStatuses.get(position);
        if (requestedDocumentStatus != null) {
            if (CommonUtils.isValidString(requestedDocumentStatus.getName()))
                subMenuHolder.docName.setText(requestedDocumentStatus.getName());

            if (CommonUtils.isValidString(requestedDocumentStatus.getStatus()))
                subMenuHolder.docStatus.setText(requestedDocumentStatus.getStatus());

            subMenuHolder.itemView.setOnClickListener(v -> statusClickListener.onClick(requestedDocumentStatus));
        }
        /*if (position == documentStatuses.size() - 1) {
            subMenuHolder.lineView.setVisibility(View.GONE);
        } else {
            subMenuHolder.lineView.setVisibility(View.VISIBLE);
        }*/
    }


    @Override
    public int getItemCount() {
        return documentStatuses.size();
    }

    class DocumentHolder extends RecyclerView.ViewHolder {
        private TextView docName, docStatus;
        private View lineView;


        DocumentHolder(@NonNull View itemView) {
            super(itemView);
            docName = itemView.findViewById(R.id.document);
            docStatus = itemView.findViewById(R.id.document_status);
            lineView = itemView.findViewById(R.id.line_view);
        }
    }

    public interface DocumentsStatusClickListener {
        void onClick(RequestedDocumentStatus requestedDocumentStatus);
    }
}
