package ch.jtaf.ui.view;

import ch.jtaf.db.tables.records.EventRecord;
import ch.jtaf.ui.dialog.EventDialog;
import ch.jtaf.ui.layout.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;

import static ch.jtaf.db.tables.Event.EVENT;
import static ch.jtaf.ui.component.GridBuilder.addActionColumnAndSetSelectionListener;

@Route(layout = MainLayout.class)
public class EventsView extends ProtectedGridView<EventRecord> {

    private static final long serialVersionUID = 1L;

    public EventsView(DSLContext dsl) {
        super(dsl, EVENT);

        setHeightFull();

        add(new H1(getTranslation("Events")));

        EventDialog dialog = new EventDialog(getTranslation("Event"));

        grid.addColumn(EventRecord::getAbbreviation).setHeader(getTranslation("Abbreviation")).setSortable(true);
        grid.addColumn(EventRecord::getName).setHeader(getTranslation("Name")).setSortable(true);
        grid.addColumn(EventRecord::getGender).setHeader(getTranslation("Gender")).setSortable(true);
        grid.addColumn(EventRecord::getEventType).setHeader(getTranslation("Event.Type")).setSortable(true);
        grid.addColumn(EventRecord::getA).setHeader("A");
        grid.addColumn(EventRecord::getB).setHeader("B");
        grid.addColumn(EventRecord::getC).setHeader("C");

        addActionColumnAndSetSelectionListener(grid, dialog, dataProvider::refreshAll, () -> {
            EventRecord newRecord = EVENT.newRecord();
            newRecord.setOrganizationId(organizationRecord.getId());
            return newRecord;
        });

        add(grid);
    }

    @Override
    public String getPageTitle() {
        return "JTAF - " + getTranslation("Events");
    }

    @Override
    protected Condition initialCondition() {
        return EVENT.ORGANIZATION_ID.eq(organizationRecord.getId());
    }

    @Override
    protected SortField<?>[] initialSort() {
        return new SortField[]{EVENT.GENDER.asc(), EVENT.ABBREVIATION.asc()};
    }
}
