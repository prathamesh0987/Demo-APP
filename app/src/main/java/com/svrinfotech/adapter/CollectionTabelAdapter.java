package com.svrinfotech.adapter;

public class CollectionTabelAdapter { //extends AbstractTableAdapter<ColumnHeader,RowHeader,Cell> {
/*
    public CollectionTabelAdapter(Context context) {
        super(context);
    }

    class CellViewHolder extends AbstractViewHolder {
        public TextView cell;
        public CellViewHolder(View itemView) {
            super(itemView);
            cell=itemView.findViewById(R.id.cell_data);
        }
    }

    class ColumnViewHolder extends AbstractViewHolder {
        public TextView column;
        public ColumnViewHolder(View itemView) {
            super(itemView);
            column=itemView.findViewById(R.id.column_data);
        }
    }

    class RowViewHolder extends AbstractViewHolder {
        public TextView row;
        public RowViewHolder(View itemView) {
            super(itemView);
            row=itemView.findViewById(R.id.row_data);
        }
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return position;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return position;
    }

    @Override
    public int getCellItemViewType(int position) {
        return position;
    }

    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_cell,parent,false);
        return new CellViewHolder(view);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {
        Cell cell=(Cell) cellItemModel;
        CellViewHolder cellViewHolder=(CellViewHolder)holder;
        cellViewHolder.cell.setText(String.valueOf(cell.getmData()));
        *//*cellViewHolder.itemView.getLayoutParams().width= LinearLayout.LayoutParams.WRAP_CONTENT;
        cellViewHolder.cell.requestLayout();*//*
    }

    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_column_header_layout,parent,false);
        return new ColumnViewHolder(view);
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int columnPosition) {
        ColumnHeader columnHeader=(ColumnHeader)columnHeaderItemModel;
        ColumnViewHolder columnViewHolder=(ColumnViewHolder)holder;
        columnViewHolder.column.setText(String.valueOf(columnHeader.getmData()));
        *//*columnViewHolder.itemView.getLayoutParams().width= LinearLayout.LayoutParams.WRAP_CONTENT;
        columnViewHolder.column.requestLayout();*//*
    }

    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_row,parent,false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int rowPosition) {
        RowHeader rowHeader=(RowHeader) rowHeaderItemModel;
        RowViewHolder rowViewHolder=(RowViewHolder)holder;
        rowViewHolder.row.setText(String.valueOf(rowHeader.getmData()));
        *//*rowViewHolder.itemView.getLayoutParams().width= LinearLayout.LayoutParams.WRAP_CONTENT;
        rowViewHolder.row.requestLayout();*//*
    }

    @Override
    public View onCreateCornerView() {
        return LayoutInflater.from(mContext).inflate(R.layout.table_corner,null);
    }*/
}
