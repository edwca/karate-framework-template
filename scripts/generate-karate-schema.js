const fs = require('fs');

function inferType(value) {
  if (value === null) return '#null';
  if (Array.isArray(value)) return value.length === 0 ? [] : [inferType(value[0])];
  if (typeof value === 'string') return '#string';
  if (typeof value === 'number') return '#number';
  if (typeof value === 'boolean') return '#boolean';
  if (typeof value === 'object') return generateSchema(value);
  return '#string';
}

function generateSchema(json) {
  const schema = {};
  for (const key in json) {
    schema[key] = inferType(json[key]);
  }
  return schema;
}

const input = process.argv[2] || 'input-response.json';
const output = process.argv[3] || 'schema-output.json';

const raw = fs.readFileSync(input, 'utf8');
const json = JSON.parse(raw);

const schema = generateSchema(json);

fs.writeFileSync(output, JSON.stringify(schema, null, 2));
console.log(`✅ Schema generado en: ${output}`);
