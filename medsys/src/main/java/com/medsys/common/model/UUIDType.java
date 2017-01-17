package com.medsys.common.model;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hsqldb.Types;

public class UUIDType extends AbstractSingleColumnStandardBasicType<UUID> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "uuid-name";
	public static final UUIDType INSTANCE = new UUIDType();
	
	//static Logger logger = LoggerFactory.getLogger(UUIDType.class);
	
	public UUIDType() {
		super(UUIDSqlTypeDescriptor.INSTANCE, UUIDTypeDescriptor.INSTANCE);
	}

	public String getName() {
		return NAME;
	}

	public static class UUIDSqlTypeDescriptor implements SqlTypeDescriptor {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public static final UUIDSqlTypeDescriptor INSTANCE = new UUIDSqlTypeDescriptor();

		public int getSqlType() {
			//logger.debug("Returning UUID type as VARCHAR");
			return Types.VARCHAR;
		}

		@Override
		public boolean canBeRemapped() {
			return true;
		}

		public <X> ValueBinder<X> getBinder(
				final JavaTypeDescriptor<X> javaTypeDescriptor) {
			return new BasicBinder<X>(javaTypeDescriptor, this) {
				@Override
				protected void doBind(PreparedStatement st, X value, int index,
						WrapperOptions options) throws SQLException {
					st.setObject(index, javaTypeDescriptor.unwrap(value,
							UUID.class, options));
				}
			};
		}

		public <X> ValueExtractor<X> getExtractor(
				final JavaTypeDescriptor<X> javaTypeDescriptor) {
			return new BasicExtractor<X>(javaTypeDescriptor, this) {
				@Override
				protected X doExtract(ResultSet rs, String name,
						WrapperOptions options) throws SQLException {
					return javaTypeDescriptor.wrap(rs.getObject(name), options);
				}

				@Override
				protected X doExtract(CallableStatement statement, int index,
						WrapperOptions options) throws SQLException {
					return javaTypeDescriptor.wrap(statement.getObject(index),
							options);
				}

				@Override
				protected X doExtract(CallableStatement statement, String name,
						WrapperOptions options) throws SQLException {
					return javaTypeDescriptor.wrap(statement.getObject(name),
							options);
				}
			};
		}
	}
}