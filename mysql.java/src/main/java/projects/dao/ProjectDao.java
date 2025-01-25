package projects.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import projects.entity.Project;
import projects.exception.DbException;
import provided.util.DaoBase;

public class ProjectDao extends DaoBase {

	private static final String CATEGORY_TABLE = "category";
	private static final String MATERIAL_TABLE = "material";
	private static final String PROJECT_TABLE = "project";
	private static final String PROJECT_CATEGORY_TABLE = "project_category";
	private static final String STEP_TABLE = "step";

	public Project insertProject(Project project) {
		// @formatter:off
		String sql = ""
				+ "INSERT INTO " + PROJECT_TABLE + " "
				+ "(project_name, estimated_hours, actual_hours, difficulty, notes) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)";
		// @formatter:on

		// Obtain a connection:
		try (Connection conn = DbConnection.getConnection()) {
			// Start a transaction using the method from DaoBase class:
			startTransaction(conn);

			// Obtain a PreparedStatement object from the Connection object:
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				// Set the parameters for the SQL insert statement using the convenience method
				// setParameter from DaoBase:
				setParameter(stmt, 1, project.getProjectName(), String.class);
				setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
				setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
				setParameter(stmt, 4, project.getDifficulty(), Integer.class);
				setParameter(stmt, 5, project.getNotes(), String.class);

				// Call executeUpdate to save the project details to the database:
				stmt.executeUpdate();

				// Obtain project ID (primary key) using getLastInsertId convenience method from
				// DaoBase:
				Integer projectId = getLastInsertId(conn, PROJECT_TABLE);

				// Commit transaction using convenience method from DaoBase:
				commitTransaction(conn);

				// Set the projectId on the Project object:
				project.setProjectId(projectId);
				return project;

			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
		} catch (SQLException e) {
			throw new DbException(e);
		}
	}

}
