import pandas as pd

# Read the CSV files into pandas DataFrames
df1 = pd.read_csv(
    r"C:\Users\Russell S\Downloads\pandas\GlobalYearlyLandTempByCountry.csv"
)
df2 = pd.read_csv(r"C:\Users\Russell S\Downloads\pandas\Population.csv")

# Reshape df2 to convert years into a single 'Year' column
df2_melted = df2.melt(
    id_vars=["Country Name", "Country Code"], var_name="Year", value_name="Population"
)
# Convert 'Year' column to integer type
df2_melted["Year"] = df2_melted["Year"].astype(int)

# Merge the DataFrames on 'Country' and 'Year'
merged_df = pd.merge(
    df1,
    df2_melted,
    left_on=["Country", "Year"],
    right_on=["Country Name", "Year"],
    how="left",
)

# Rename the columns in the merged DataFrame
merged_df = merged_df.rename(
    columns={
        "Country Name": "Country 2",
        "Country Code": "Country Code",
        "Population": "Population",
    }
)

print(merged_df.head())

# Drop columns
merged_df = merged_df.drop(columns=["Country 2", "Country Code"])

print(merged_df.head())

# Fill missing population values with 0
merged_df["Population"] = merged_df["Population"].fillna(0)

# Save the merged DataFrame to a CSV file
merged_df.to_csv(r"C:\Users\Russell S\Downloads\pandas\MergedData.csv", index=False)
