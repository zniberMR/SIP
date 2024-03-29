package ma.ac.ehtp.sip.tools;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

import org.joda.time.DateTime;

import java.sql.Timestamp;

public class JodaDateTimeConverter implements Converter {

    private static final long serialVersionUID = 1L;

    @Override
    public Object convertDataValueToObjectValue( Object dataValue, Session session ) {
        return dataValue == null ? null : new DateTime( (Timestamp) dataValue );
    }

    @Override
    public Object convertObjectValueToDataValue( Object objectValue, Session session ) {
        return objectValue == null ? null : new Timestamp( ( (DateTime) objectValue ).getMillis() );
    }

    @Override
    public void initialize(DatabaseMapping mapping, Session session ) {
    }

    @Override
    public boolean isMutable() {
        return false;
    }

}